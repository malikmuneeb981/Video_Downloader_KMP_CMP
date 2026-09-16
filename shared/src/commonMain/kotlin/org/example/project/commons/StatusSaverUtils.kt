package org.example.project.commons

import androidx.compose.runtime.Composable
import com.cyberarsenals.video.downloader.save.videos.domain.models.appmodels.WsStatusModel
import org.example.project.presentation.viewModels.DownloaderViewModel
import org.koin.core.module.Module


@Composable
expect fun RecentStatuses(
    photoOrVideo : Int= 1,
    downloaderViewModel: DownloaderViewModel,
    onResult: (List<WsStatusModel>) -> Unit
)

expect fun provideDownloadStatusModule(): Module

interface DownloadStatus{
    suspend fun downloadStatus(
        isVideo: Boolean,
        fileUri: String,
        fileName: String,
        appName: String
    )
}