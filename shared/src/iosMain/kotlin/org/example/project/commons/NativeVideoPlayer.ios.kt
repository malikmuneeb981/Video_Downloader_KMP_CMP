package org.example.project.commons

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitView
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.readValue
import platform.AVFAudio.AVAudioSession
import platform.AVFAudio.AVAudioSessionCategoryPlayback
import platform.AVFAudio.setActive
import platform.AVFoundation.*
import platform.CoreGraphics.CGRectMake
import platform.CoreMedia.CMTimeGetSeconds
import platform.CoreMedia.CMTimeMake
import platform.CoreMedia.CMTimeMakeWithSeconds
import platform.CoreMedia.kCMTimeZero
import platform.Foundation.NSNotificationCenter
import platform.Foundation.NSOperationQueue
import platform.Foundation.NSURL
import platform.QuartzCore.CATransaction
import platform.UIKit.UIView
import platform.darwin.dispatch_get_main_queue

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
class IosVideoPlayerController(
    val avPlayer: AVPlayer,
    val playerItem: AVPlayerItem,
    val playerLayer: AVPlayerLayer
) : NativeVideoPlayerController {

    override var currentPositionMs by mutableStateOf(0L)
    override var durationMs by mutableStateOf(0L)
    override var isPlaying by mutableStateOf(false)
    override var isBuffering by mutableStateOf(false)

    private var isLooping = false

    override fun play() {
        avPlayer.play()
        isPlaying = true
    }

    override fun pause() {
        avPlayer.pause()
        isPlaying = false
    }

    override fun seekTo(positionMs: Long) {
        val cmTime = CMTimeMakeWithSeconds(positionMs / 1000.0, 1000)
        avPlayer.seekToTime(cmTime)
        currentPositionMs = positionMs
    }

    override fun setVolume(volume: Float) {
        avPlayer.volume = volume.coerceIn(0f, 1f)
    }

    override fun setPlaybackSpeed(speed: Float) {
        avPlayer.rate = speed
    }

    override fun setLooping(isLooping: Boolean) {
        this.isLooping = isLooping
    }

    fun checkLooping() = isLooping
}

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
@Composable
actual fun rememberNativeVideoPlayer(
    source: String,
    autoPlay: Boolean,
    isLooping: Boolean
): NativeVideoPlayerController {
    val nsUrl = remember(source) {
        if (source.startsWith("http://") || source.startsWith("https://") || source.startsWith("file://")) {
            NSURL.URLWithString(source)
        } else {
            NSURL.fileURLWithPath(source)
        }
    }

    val playerItem = remember(nsUrl) {
        nsUrl?.let { AVPlayerItem(uRL = it) } ?: AVPlayerItem(uRL = NSURL())
    }

    val avPlayer = remember(playerItem) {
        try {
            val audioSession = AVAudioSession.sharedInstance()
            audioSession.setCategory(AVAudioSessionCategoryPlayback, error = null)
            audioSession.setActive(true, error = null)
        } catch (_: Exception) {}

        AVPlayer.playerWithPlayerItem(playerItem).apply {
            actionAtItemEnd = AVPlayerActionAtItemEndNone
        }
    }

    val playerLayer = remember(avPlayer) {
        AVPlayerLayer.playerLayerWithPlayer(avPlayer).apply {
            videoGravity = AVLayerVideoGravityResizeAspect
        }
    }

    val controller = remember(avPlayer, playerItem) {
        IosVideoPlayerController(avPlayer, playerItem, playerLayer).apply {
            setLooping(isLooping)
        }
    }

    DisposableEffect(playerItem, avPlayer) {
        val timeObserver = avPlayer.addPeriodicTimeObserverForInterval(
            interval = CMTimeMake(1, 10),
            queue = dispatch_get_main_queue()
        ) { time ->
            val sec = CMTimeGetSeconds(time)
            if (!sec.isNaN() && sec >= 0) controller.currentPositionMs = (sec * 1000).toLong()

            val durSec = CMTimeGetSeconds(playerItem.duration)
            if (!durSec.isNaN() && durSec > 0) controller.durationMs = (durSec * 1000).toLong()
        }

        val endObserver = NSNotificationCenter.defaultCenter.addObserverForName(
            name = AVPlayerItemDidPlayToEndTimeNotification,
            `object` = playerItem,
            queue = NSOperationQueue.mainQueue
        ) { _ ->
            if (controller.checkLooping()) {
                avPlayer.seekToTime(kCMTimeZero.readValue())
                avPlayer.play()
            } else {
                controller.pause()
            }
        }

        if (autoPlay) controller.play()

        onDispose {
            NSNotificationCenter.defaultCenter.removeObserver(endObserver)
            avPlayer.removeTimeObserver(timeObserver)
            avPlayer.pause()
        }
    }

    return controller
}

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun NativeVideoSurface(
    controller: NativeVideoPlayerController,
    modifier: Modifier,
    resizeMode: VideoResizeMode
) {
    val iosController = controller as? IosVideoPlayerController ?: return
    val layer = iosController.playerLayer

    val gravity = when (resizeMode) {
        VideoResizeMode.Fit -> AVLayerVideoGravityResizeAspect
        VideoResizeMode.Zoom -> AVLayerVideoGravityResizeAspectFill
        VideoResizeMode.Fill -> AVLayerVideoGravityResize
    }
    layer.videoGravity = gravity

    UIKitView(
        factory = {
            val container = object : UIView(CGRectMake(0.0, 0.0, 0.0, 0.0)) {
                override fun layoutSubviews() {
                    super.layoutSubviews()
                    CATransaction.begin()
                    CATransaction.setDisableActions(true)
                    layer.frame = bounds
                    CATransaction.commit()
                }
            }
            container.layer.addSublayer(layer)
            container
        },
        update = { view ->
            CATransaction.begin()
            CATransaction.setDisableActions(true)
            layer.frame = view.bounds
            CATransaction.commit()
        },
        modifier = modifier.fillMaxSize(),
        onRelease = { layer.removeFromSuperlayer() }
    )
}
