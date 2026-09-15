package org.example.project.commons

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCObjectVar
import kotlinx.cinterop.memScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.StringResource
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSDate
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSError
import platform.Foundation.NSFileManager
import platform.Foundation.NSFileModificationDate
import platform.Foundation.NSOperationQueue
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSString
import platform.Foundation.NSURL
import platform.Foundation.NSURLSession
import platform.Foundation.NSURLSessionConfiguration
import platform.Foundation.NSURLSessionDownloadDelegateProtocol
import platform.Foundation.NSURLSessionDownloadTask
import platform.Foundation.NSURLSessionTask
import platform.Foundation.NSUUID
import platform.Foundation.NSUserDomainMask
import platform.Foundation.pathExtension
import platform.Foundation.timeIntervalSince1970
import platform.darwin.NSObject
import kotlin.coroutines.resume
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import platform.Foundation.*


class DownloadFileManagerIOS:DownloadFileManager {

    private val session: NSURLSession = NSURLSession.sessionWithConfiguration(
        configuration = NSURLSessionConfiguration.backgroundSessionConfigurationWithIdentifier("com.your.app.downloader"),
        delegate = DownloadDelegate(), // Your delegate here
        delegateQueue = NSOperationQueue.mainQueue
    )
    private val activeTasks = mutableMapOf<Long, NSURLSessionDownloadTask>()
    override fun downloadFile(vidUrl: String): Long {
        val nsUrl = NSURL.URLWithString(vidUrl) ?: return -1L

        val task = session.downloadTaskWithURL(nsUrl)
        val downloadId = task.taskIdentifier.toLong()

        // 2. Save the task into the map before starting it
        activeTasks[downloadId] = task

        task.resume()
        return downloadId
    }

    override suspend fun getDownloadProgress(downloadId: Long): Int {
        // 3. Fetch the exact task from your map
        val task = activeTasks[downloadId]

        if (task != null) {
            val received = task.countOfBytesReceived
            val expected = task.countOfBytesExpectedToReceive
            // Check if the task has an error (failed instantly)
            if (task.error != null) {
                println("Download failed with error: ${task.error?.localizedDescription}")
                activeTasks.remove(downloadId) // Clean up
                return 0
            }
            return if (expected > 0) {
                val progress = ((received * 100) / expected).toInt()
                println("Progress Of Downloads $progress")

                // Optional: Clean up if finished
                if (progress >= 100) {
                    activeTasks.remove(downloadId)
                }

                progress
            } else {
                println("Progress Of Downloads Not Found (waiting for size)")
                0
            }
        } else {
            println("Task $downloadId is null - it was not found in the map.")
            return 0
        }
    }
    override suspend fun removeDownload(downloadId: Long) {
        // 4. Cancel using the map
        val task = activeTasks[downloadId]
        task?.cancel()
        activeTasks.remove(downloadId)
    }

    @OptIn(ExperimentalForeignApi::class)
    override suspend fun getDownloadedFiles(statuses: Boolean, appName: StringResource): List<String> {
        return withContext(Dispatchers.Default) {
            val fileManager = NSFileManager.defaultManager

            // 1. Get the iOS Documents Directory
            val documentsUrl = fileManager.URLForDirectory(
                directory = NSDocumentDirectory,
                inDomain = NSUserDomainMask,
                appropriateForURL = null,
                create = false,
                error = null
            ) ?: return@withContext emptyList()

            // 2. We use the Documents folder path directly since that's where you saved them!
            // (Removed the folderUrl logic that appended appName/folderName)
            val folderPath = documentsUrl.path ?: return@withContext emptyList()

            val allowedExtensions = if (statuses) listOf("mp4", "jpg") else listOf("mp4", "avi", "mov", "mkv")

            // Temporary data class for sorting by iOS NSDate
            data class FileData(val path: String, val lastModified: Double)

            val videoFiles = mutableListOf<FileData>()

            // 3. Read the contents of the Documents directory
            val filesInDir = fileManager.contentsOfDirectoryAtPath(folderPath, null) as? List<String>

            filesInDir?.forEach { fileName ->
                val fileExtension = (fileName as NSString).pathExtension.lowercase()

                // 4. Check if it matches allowed extensions
                if (allowedExtensions.contains(fileExtension)) {
                    val fullFilePath = "$folderPath/$fileName"

                    // 5. Get file attributes for the Last Modified Date
                    val attributes = fileManager.attributesOfItemAtPath(fullFilePath, null)
                    val modificationDate = attributes?.get(NSFileModificationDate) as? NSDate

                    val timestamp = modificationDate?.timeIntervalSince1970 ?: 0.0

                    println("File found: $fileName")
                    videoFiles.add(FileData(fullFilePath, timestamp))
                }
            }

            println("Files Count Total: ${videoFiles.size}")

            // 6. Sort descending by timestamp and return just the String paths
            videoFiles
                .sortedByDescending { it.lastModified }
                .map { it.path }
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    override suspend fun deleteFileFromFolder(filePath:String){
        val fileManager = NSFileManager.defaultManager
        if (fileManager.fileExistsAtPath(filePath)) {
            fileManager.removeItemAtPath(filePath, null)
        }
    }
}

actual fun provideDownloadManagerModule() : Module{

    return module {
        single<DownloadFileManager>{
            DownloadFileManagerIOS()
        }
    }

}
private class DownloadDelegate : NSObject(), NSURLSessionDownloadDelegateProtocol {

    @OptIn(ExperimentalForeignApi::class)
    override fun URLSession(
        session: NSURLSession,
        downloadTask: NSURLSessionDownloadTask,
        didFinishDownloadingToURL: NSURL
    ) {
        val fileManager = NSFileManager.defaultManager

        val documentsPath = NSSearchPathForDirectoriesInDomains(
            NSDocumentDirectory,
            NSUserDomainMask,
            true
        ).first() as String
        // Destination path in iOS Documents directory
        val destinationUrl = NSURL.fileURLWithPath("$documentsPath/video_${getCurrentTimeMillis()}.mp4")
        // In Kotlin/Native, we use memScoped to handle C-style error pointers
        memScoped {
            val errorPtr = alloc<ObjCObjectVar<NSError?>>()

            // moveItemAtURL returns true if successful, false if it failed
            val success = fileManager.moveItemAtURL(
                srcURL = didFinishDownloadingToURL,
                toURL = destinationUrl,
                error = errorPtr.ptr
            )
            if (success) {
                println("✅ Download finished and saved to: ${destinationUrl.path}")

                // If you are using the activeTasks Map from the previous step,
                // you might want to remove the task from the map here!
            } else {
                val errorMsg = errorPtr.value?.localizedDescription ?: "Unknown error"
                println("❌ Failed to save downloaded file: $errorMsg")
            }
        }
    }
    // Optional but highly recommended: Catch download failures (like bad internet or invalid URLs)
    override fun URLSession(
        session: NSURLSession,
        task: NSURLSessionTask,
        didCompleteWithError: NSError?
    ) {
        if (didCompleteWithError != null) {
            println("❌ Download Task Failed: ${didCompleteWithError.localizedDescription}")
        }
    }
}