package org.example.project.commons

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import androidx.core.net.toUri
import downloaderkmpproductionapp.shared.generated.resources.Res
import downloaderkmpproductionapp.shared.generated.resources.app_name
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.StringResource
import org.koin.core.module.Module
import org.koin.dsl.module
import java.io.File

class DownloadFileManagerAndroid(val downloadManager: DownloadManager):DownloadFileManager {
    override fun downloadFile(vidUrl: String): Long {
        val request = DownloadManager.Request(vidUrl.toUri())
            .setAllowedNetworkTypes(
                DownloadManager.Request.NETWORK_WIFI or
                        DownloadManager.Request.NETWORK_MOBILE
            )
            .setTitle("Download")
            .setDescription("Downloading video...")
            .setNotificationVisibility(
                DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED
            )
            .setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DCIM,
                "${Res.string.app_name}/Downloaded Videos/${generateVideoName()}.mp4"
            )

        return downloadManager.enqueue(request)
    }
    override suspend fun getDownloadProgress(downloadId: Long): Int{
        return withContext(Dispatchers.IO) {
            var progress = 0
            val query = DownloadManager.Query().setFilterById(downloadId)
            val cursor = downloadManager.query(query)
            if (cursor != null && cursor.moveToFirst()) {
                val totalSizeIndex = cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES)
                val downloadedSizeIndex = cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)
                if (totalSizeIndex >= 0 && downloadedSizeIndex >= 0) {
                    val totalSize = cursor.getLong(totalSizeIndex)
                    val downloadedSize = cursor.getLong(downloadedSizeIndex)
                    if (totalSize > 0) {
                        progress = ((downloadedSize * 100) / totalSize).toInt()
                    }
                }
                cursor.close()
            }
            progress
        }
    }
    override suspend fun removeDownload(downloadId: Long){
        downloadManager.remove(downloadId)
    }
    override suspend fun getDownloadedFiles(statuses: Boolean, appName: StringResource): List<String> {
        return withContext(Dispatchers.IO) {
            val folderName = if (statuses) "Downloaded Statuses" else "Downloaded Videos"
            val folder = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),
                "$appName/$folderName"
            )
            val videoFiles = mutableListOf<File>()
            if (folder.exists() && folder.isDirectory) {
                folder.listFiles()?.forEach { file ->
                    val allowedExtensions = if (statuses) listOf("mp4", "jpg") else listOf("mp4", "avi", "mov", "mkv")

                    if (file.isFile && file.extension.lowercase() in allowedExtensions) {
                        videoFiles.add(file)
                    }
                }
            }
            videoFiles
                .sortedByDescending { it.lastModified() }
                .map { it.absolutePath }
        }
    }
    override suspend fun deleteFileFromFolder(filePath:String){
        val file = File(filePath)
        file.exists() && file.delete()
    }

    fun generateVideoName(): String {
        return "VID_${getCurrentTimeMillis()}.mp4"
    }
}

actual fun provideDownloadManagerModule() : Module{

    return module {
        single<DownloadFileManager>{
            DownloadFileManagerAndroid(get())
        }
    }

}