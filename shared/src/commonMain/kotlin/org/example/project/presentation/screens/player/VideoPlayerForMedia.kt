package org.example.project.presentation.screens.player

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import org.example.project.commons.FullScreenAndHideBars
import org.example.project.presentation.composables.VideoPlayer
import org.example.project.presentation.viewModels.DownloaderViewModel
import org.example.project.presentation.viewModels.MediaReaderViewModel

@Composable
fun VideoPlayerForMedia(
    navController: NavController,
    downloaderViewModel: DownloaderViewModel,
    mediaReaderViewModel: MediaReaderViewModel
) {
    FullScreenAndHideBars()

    val source = downloaderViewModel.videoToPlayUri
        ?: downloaderViewModel.videoToPlayFile
        ?: downloaderViewModel.videoToPlayUrl
        ?: ""

    val videoTitle = downloaderViewModel.videoToPlayFile
        ?: downloaderViewModel.videoToPlayUrl?.substringAfterLast("/")
        ?: downloaderViewModel.videoToPlayUri?.substringAfterLast("/")
        ?: "Video Player"

    Box(modifier = Modifier.fillMaxSize()) {
        if (source.isNotBlank()) {
            VideoPlayer(
                initialMediaSource = source,
                videoTitle = videoTitle,
                downloaderViewModel = downloaderViewModel,
                mediaReaderViewModel = mediaReaderViewModel,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}