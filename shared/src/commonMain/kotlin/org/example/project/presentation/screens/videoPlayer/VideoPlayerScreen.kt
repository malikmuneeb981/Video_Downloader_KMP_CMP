package org.example.project.presentation.screens.videoPlayer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import chaintech.videoplayer.host.MediaPlayerError
import chaintech.videoplayer.host.MediaPlayerEvent
import chaintech.videoplayer.host.MediaPlayerHost
import chaintech.videoplayer.model.PlayerSpeed
import chaintech.videoplayer.model.ScreenResize
import chaintech.videoplayer.ui.video.VideoPlayerComposable
import downloaderkmpproductionapp.shared.generated.resources.Res
import downloaderkmpproductionapp.shared.generated.resources.ic_backarrow
import org.example.project.commons.BackHandler
import org.example.project.commons.MediaPermission
import org.example.project.presentation.viewModels.DownloaderViewModel
import org.jetbrains.compose.resources.vectorResource

@Composable
fun VideoPlayerScreen(
    navController: NavController,
    downloaderViewModel: DownloaderViewModel
){
    val videoPlayerHost = MediaPlayerHost(
        mediaUrl = downloaderViewModel.videoToPlayFile?:downloaderViewModel.videoToPlayUrl?:"",
        autoPlay = true,
        isMuted = false,
        initialSpeed = PlayerSpeed.X1,
        initialVideoFitMode = ScreenResize.FILL,
        isLooping = false,
        startTimeInSeconds = 0f,
        isFullScreen = false
    )

    videoPlayerHost.onEvent = { event ->
        when (event) {
            is MediaPlayerEvent.MuteChange -> { println("Mute status changed: ${event.isMuted}") }
            is MediaPlayerEvent.PauseChange -> { println("Pause status changed: ${event.isPaused}") }
            is MediaPlayerEvent.BufferChange -> { println("Buffering status: ${event.isBuffering}") }
            is MediaPlayerEvent.CurrentTimeChange -> { println("Current playback time: ${event.currentTime}s") }
            is MediaPlayerEvent.TotalTimeChange -> { println("Video duration updated: ${event.totalTime}s") }
            is MediaPlayerEvent.FullScreenChange -> { println("FullScreen status changed: ${event.isFullScreen}") }
            MediaPlayerEvent.MediaEnd -> { println("Video playback ended") }
            else -> { println("Video playback ended") }
        }
    }

    videoPlayerHost.onError = { error ->
        when(error) {
            is MediaPlayerError.VlcNotFound -> { println("Error: VLC library not found. Please ensure VLC is installed.") }
            is MediaPlayerError.InitializationError -> { println("Initialization Error: ${error.details}") }
            is MediaPlayerError.PlaybackError -> { println("Playback Error: ${error.details}") }
            is MediaPlayerError.ResourceError -> { println("Resource Error: ${error.details}") }
        }
    }
    val playerHost = remember { videoPlayerHost }

    BackHandler {
        navController.popBackStack()
    }
    Scaffold {
        Box(modifier = Modifier.fillMaxSize().padding(it),
            contentAlignment = Alignment.TopStart) {

            VideoPlayerComposable(
                modifier = Modifier.fillMaxSize(),
                playerHost = playerHost
            )
            Box(modifier = Modifier.padding(start = 15.dp).padding(
                top = 10.dp
            ).background(
                color = Color.Black.copy(
                    alpha = 0.4f
                ),
                shape = RoundedCornerShape(30.dp)
            ).clickable{
                navController.popBackStack()
            }.padding(10.dp)){
                Image(imageVector = vectorResource(Res.drawable.ic_backarrow),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(
                        color = Color.White
                    ))
            }
        }

    }

}