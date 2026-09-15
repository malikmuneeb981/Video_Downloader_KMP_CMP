package org.example.project.commons

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asComposeImageBitmap
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import org.jetbrains.skia.Image
import platform.AVFoundation.AVAssetImageGenerator
import platform.AVFoundation.AVURLAsset
import platform.CoreMedia.CMTimeMakeWithSeconds
import platform.Foundation.NSURL
import platform.UIKit.UIImage
import platform.UIKit.UIImageJPEGRepresentation
import platform.posix.memcpy

private val iosThumbnailCache = mutableMapOf<String, ImageBitmap>()

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
actual suspend fun loadVideoThumbnail(urlOrPath: String): ImageBitmap? = withContext(Dispatchers.IO) {
    if (urlOrPath.isBlank()) return@withContext null
    iosThumbnailCache[urlOrPath]?.let { return@withContext it }

    try {
        val nsUrl = if (urlOrPath.startsWith("http://") || urlOrPath.startsWith("https://") || urlOrPath.startsWith("file://")) {
            NSURL.URLWithString(urlOrPath)
        } else {
            NSURL.fileURLWithPath(urlOrPath)
        } ?: return@withContext null

        val asset = AVURLAsset(uRL = nsUrl, options = null)
        val imageGenerator = AVAssetImageGenerator(asset = asset).apply {
            appliesPreferredTrackTransform = true
        }

        val cgImage = try {
            imageGenerator.copyCGImageAtTime(CMTimeMakeWithSeconds(1.0, 600), actualTime = null, error = null)
        } catch (_: Exception) {
            null
        } ?: try {
            imageGenerator.copyCGImageAtTime(CMTimeMakeWithSeconds(0.0, 600), actualTime = null, error = null)
        } catch (_: Exception) {
            null
        } ?: return@withContext null

        val uiImage = UIImage.imageWithCGImage(cgImage)
        val nsData = UIImageJPEGRepresentation(uiImage, 0.8) ?: return@withContext null
        val length = nsData.length.toInt()
        if (length == 0) return@withContext null

        val bytes = ByteArray(length)
        bytes.usePinned { pinned ->
            memcpy(pinned.addressOf(0), nsData.bytes, nsData.length)
        }
        val skiaImage = Image.makeFromEncoded(bytes)
        val skiaBitmap = org.jetbrains.skia.Bitmap.makeFromImage(skiaImage)
        val imageBitmap = skiaBitmap.asComposeImageBitmap()
        iosThumbnailCache[urlOrPath] = imageBitmap
        imageBitmap
    } catch (_: Exception) {
        null
    }
}
