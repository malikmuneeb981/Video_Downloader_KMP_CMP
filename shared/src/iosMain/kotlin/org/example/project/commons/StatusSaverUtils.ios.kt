package org.example.project.commons

import androidx.compose.runtime.Composable
import com.cyberarsenals.video.downloader.save.videos.domain.models.appmodels.WsStatusModel
import org.example.project.presentation.viewModels.DownloaderViewModel
import org.koin.core.module.Module

@Composable
actual fun RecentStatuses(
    photoOrVideo: Int,
    downloaderViewModel: DownloaderViewModel,
    onResult: (List<WsStatusModel>) -> Unit
) {
}

actual fun provideDownloadStatusModule(): Module {
    TODO("Not yet implemented")
}