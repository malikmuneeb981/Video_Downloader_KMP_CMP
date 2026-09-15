package org.example.project.commons

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.example.project.domain.models.appmodels.MediaFile
import org.example.project.domain.models.appmodels.MediaFolder
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.AVFoundation.AVURLAsset
import platform.CoreMedia.CMTimeGetSeconds
import platform.Foundation.NSDate
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSFileModificationDate
import platform.Foundation.NSFileSize
import platform.Foundation.NSNumber
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask
import platform.Foundation.pathExtension
import platform.Foundation.lastPathComponent
import platform.Foundation.timeIntervalSince1970

@OptIn(ExperimentalForeignApi::class)
class IosFileManager : FileManager {

    private val fileManager = NSFileManager.defaultManager
    private val videoExtensions = setOf("mp4", "mov", "m4v", "avi", "mkv", "webm", "3gp", "flv")

    override suspend fun loadMedia(): Map<String, MediaFolder> = withContext(Dispatchers.Default) {
        val foldersMap = mutableMapOf<String, MediaFolder>()
        val allVideos = scanDirectoryForVideos()

        for (mediaFile in allVideos) {
            val folder = foldersMap.getOrPut(mediaFile.folderName) {
                MediaFolder(folderName = mediaFile.folderName, files = mutableListOf())
            }
            folder.files.add(mediaFile)
        }

        foldersMap
    }

    override suspend fun loadVideos(): List<MediaFile> = withContext(Dispatchers.Default) {
        scanDirectoryForVideos().sortedByDescending { it.dateModified }
    }

    private fun scanDirectoryForVideos(): List<MediaFile> {
        val documentsUrl = fileManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null
        ) ?: return emptyList()

        val documentsPath = documentsUrl.path ?: return emptyList()
        val videoFiles = mutableListOf<MediaFile>()

        val enumerator = fileManager.enumeratorAtPath(documentsPath)
        while (true) {
            val relativePath = enumerator?.nextObject() as? String ?: break
            val fullPath = "$documentsPath/$relativePath"
            val fileUrl = NSURL.fileURLWithPath(fullPath)
            val ext = fileUrl.pathExtension?.lowercase() ?: ""

            if (videoExtensions.contains(ext)) {
                val attributes = fileManager.attributesOfItemAtPath(fullPath, error = null)
                val fileSize = (attributes?.get(NSFileSize) as? NSNumber)?.longLongValue ?: 0L
                val modDate = attributes?.get(NSFileModificationDate) as? NSDate
                val dateModifiedMs = ((modDate?.timeIntervalSince1970 ?: 0.0) * 1000).toLong()

                val fileName = fileUrl.lastPathComponent ?: relativePath
                val parentFolder = fileUrl.URLByDeletingLastPathComponent?.lastPathComponent ?: "Documents"

                val asset = AVURLAsset.assetWithURL(fileUrl)
                val durationSec = CMTimeGetSeconds(asset.duration)
                val durationMs = if (!durationSec.isNaN() && durationSec > 0) (durationSec * 1000).toLong() else 0L

                val mediaFile = MediaFile(
                    uri = fileUrl.absoluteString ?: fullPath,
                    path = fullPath,
                    folderName = parentFolder,
                    name = fileName,
                    size = fileSize,
                    dateModified = dateModifiedMs,
                    thumbnail = fileUrl.absoluteString ?: fullPath,
                    duration = durationMs
                )
                videoFiles.add(mediaFile)
            }
        }
        return videoFiles
    }
}

actual fun provideFilesManagerModule(): Module {
    return module {
        single<FileManager> {
            IosFileManager()
        }
    }
}