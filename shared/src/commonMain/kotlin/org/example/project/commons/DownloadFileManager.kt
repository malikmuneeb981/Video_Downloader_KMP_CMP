package org.example.project.commons

import org.jetbrains.compose.resources.StringResource
import org.koin.core.module.Module

interface DownloadFileManager{
    fun downloadFile(vidUrl: String): Long

    suspend fun getDownloadProgress(downloadId: Long): Int

    suspend fun removeDownload(downloadId: Long)

    suspend fun getDownloadedFiles(statuses: Boolean, appName: StringResource): List<String>

    suspend fun deleteFileFromFolder(filePath:String)
}
expect fun provideDownloadManagerModule() : Module