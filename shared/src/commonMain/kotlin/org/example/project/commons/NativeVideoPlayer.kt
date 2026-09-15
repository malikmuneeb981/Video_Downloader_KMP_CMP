package org.example.project.commons

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

enum class VideoResizeMode {
    Fit, Zoom, Fill
}

interface NativeVideoPlayerController {
    val currentPositionMs: Long
    val durationMs: Long
    val isPlaying: Boolean
    val isBuffering: Boolean

    fun play()
    fun pause()
    fun seekTo(positionMs: Long)
    fun setVolume(volume: Float)
    fun setPlaybackSpeed(speed: Float)
    fun setLooping(isLooping: Boolean)
}

@Composable
expect fun rememberNativeVideoPlayer(
    source: String,
    autoPlay: Boolean = true,
    isLooping: Boolean = false
): NativeVideoPlayerController

@Composable
expect fun NativeVideoSurface(
    controller: NativeVideoPlayerController,
    modifier: Modifier = Modifier,
    resizeMode: VideoResizeMode = VideoResizeMode.Fit
)
