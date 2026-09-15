package org.example.project.commons

import android.net.Uri
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackParameters
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import kotlinx.coroutines.delay
import kotlin.math.max
import androidx.core.net.toUri
import kotlin.time.Duration.Companion.milliseconds

class AndroidVideoPlayerController(
    val exoPlayer: ExoPlayer
) : NativeVideoPlayerController {

    override var currentPositionMs by mutableLongStateOf(0L)
    override var durationMs by mutableLongStateOf(0L)
    override var isPlaying by mutableStateOf(exoPlayer.isPlaying)
    override var isBuffering by mutableStateOf(false)

    fun update() {
        currentPositionMs = max(0L, exoPlayer.currentPosition)
        durationMs = max(0L, exoPlayer.duration)
        isPlaying = exoPlayer.isPlaying
        isBuffering = exoPlayer.playbackState == Player.STATE_BUFFERING
    }

    override fun play() {
        exoPlayer.play()
    }

    override fun pause() {
        exoPlayer.pause()
    }

    override fun seekTo(positionMs: Long) {
        exoPlayer.seekTo(positionMs)
        currentPositionMs = positionMs
    }

    override fun setVolume(volume: Float) {
        exoPlayer.volume = volume.coerceIn(0f, 1f)
    }

    override fun setPlaybackSpeed(speed: Float) {
        exoPlayer.playbackParameters = PlaybackParameters(speed)
    }

    override fun setLooping(isLooping: Boolean) {
        exoPlayer.repeatMode = if (isLooping) Player.REPEAT_MODE_ONE else Player.REPEAT_MODE_OFF
    }
}

@Composable
actual fun rememberNativeVideoPlayer(
    source: String,
    autoPlay: Boolean,
    isLooping: Boolean
): NativeVideoPlayerController {
    val context = LocalContext.current

    val exoPlayer = remember(source) {
        ExoPlayer.Builder(context).build().apply {
            if (source.isNotBlank()) {
                val uri = if (source.startsWith("content://") || source.startsWith("file://") || source.startsWith("http://") || source.startsWith("https://")) {
                    source.toUri()
                } else {
                    "file://$source".toUri()
                }
                setMediaItem(MediaItem.fromUri(uri))
                prepare()
                playWhenReady = autoPlay
                repeatMode = if (isLooping) Player.REPEAT_MODE_ONE else Player.REPEAT_MODE_OFF
            }
        }
    }

    val controller = remember(exoPlayer) {
        AndroidVideoPlayerController(exoPlayer)
    }

    LaunchedEffect(exoPlayer) {
        while (true) {
            controller.update()
            delay(200.milliseconds)
        }
    }

    DisposableEffect(exoPlayer) {
        onDispose {
            exoPlayer.release()
        }
    }

    return controller
}

@OptIn(UnstableApi::class)
@Composable
actual fun NativeVideoSurface(
    controller: NativeVideoPlayerController,
    modifier: Modifier,
    resizeMode: VideoResizeMode
) {
    val androidController = controller as? AndroidVideoPlayerController ?: return

    val exoResize = when (resizeMode) {
        VideoResizeMode.Fit -> AspectRatioFrameLayout.RESIZE_MODE_FIT
        VideoResizeMode.Zoom -> AspectRatioFrameLayout.RESIZE_MODE_ZOOM
        VideoResizeMode.Fill -> AspectRatioFrameLayout.RESIZE_MODE_FILL
    }

    AndroidView(
        factory = { ctx ->
            PlayerView(ctx).apply {
                player = androidController.exoPlayer
                useController = false
                this.resizeMode = exoResize
            }
        },
        update = { view ->
            view.resizeMode = exoResize
        },
        modifier = modifier.fillMaxSize()
    )
}
