package org.example.project.commons

import android.media.MediaMetadataRetriever
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private val androidThumbnailCache = mutableMapOf<String, ImageBitmap>()

actual suspend fun loadVideoThumbnail(urlOrPath: String): ImageBitmap? = withContext(Dispatchers.IO) {
    if (urlOrPath.isBlank()) return@withContext null
    androidThumbnailCache[urlOrPath]?.let { return@withContext it }

    val retriever = MediaMetadataRetriever()
    try {
        if (urlOrPath.startsWith("http://") || urlOrPath.startsWith("https://")) {
            retriever.setDataSource(urlOrPath, HashMap<String, String>())
        } else {
            retriever.setDataSource(urlOrPath)
        }
        val bitmap = retriever.getFrameAtTime(1000000, MediaMetadataRetriever.OPTION_CLOSEST_SYNC)
            ?: retriever.getFrameAtTime(0, MediaMetadataRetriever.OPTION_CLOSEST_SYNC)
            ?: retriever.frameAtTime
        val imageBitmap = bitmap?.asImageBitmap()
        if (imageBitmap != null) {
            androidThumbnailCache[urlOrPath] = imageBitmap
        }
        imageBitmap
    } catch (_: Exception) {
        null
    } finally {
        try {
            retriever.release()
        } catch (_: Exception) {}
    }
}
