package org.example.project.commons

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitView
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.CValue
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.readValue
import platform.AVFAudio.AVAudioSession
import platform.AVFAudio.AVAudioSessionCategoryPlayback
import platform.AVFAudio.setActive
import platform.AVFoundation.AVLayerVideoGravityResizeAspectFill
import platform.AVFoundation.AVPlayer
import platform.AVFoundation.AVPlayerActionAtItemEndNone
import platform.AVFoundation.AVPlayerItem
import platform.AVFoundation.AVPlayerItemDidPlayToEndTimeNotification
import platform.AVFoundation.AVPlayerLayer
import platform.AVFoundation.actionAtItemEnd
import platform.AVFoundation.addPeriodicTimeObserverForInterval
import platform.AVFoundation.muted
import platform.AVFoundation.pause
import platform.AVFoundation.play
import platform.AVFoundation.removeTimeObserver
import platform.AVFoundation.seekToTime
import platform.CoreGraphics.CGRect
import platform.CoreGraphics.CGRectMake
import platform.CoreMedia.CMTimeMake
import platform.CoreMedia.kCMTimeZero
import platform.Foundation.NSNotificationCenter
import platform.Foundation.NSOperationQueue
import platform.Foundation.NSURL
import platform.QuartzCore.CATransaction
import platform.UIKit.UIView

import platform.darwin.dispatch_get_main_queue

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
@Composable
actual fun ReelsView(
    url: String,
    isPlaying: Boolean,
    isMuted: Boolean,
    modifier: Modifier,
    onBufferingStateChanged: (Boolean) -> Unit
) {
    val nsUrl = remember(url) { 
        if (url.isNotBlank()) NSURL.URLWithString(url.trim()) else null 
    } ?: return

    val playerItem = remember(nsUrl) { AVPlayerItem(uRL = nsUrl) }
    val avPlayer = remember(playerItem) {
        // Setup Audio Session for playback
        try {
            val audioSession = AVAudioSession.sharedInstance()
            audioSession.setCategory(AVAudioSessionCategoryPlayback, error = null)
            audioSession.setActive(true, error = null)
        } catch (_: Exception) {}

        AVPlayer.playerWithPlayerItem(playerItem).apply {
            muted = isMuted
            actionAtItemEnd = AVPlayerActionAtItemEndNone
        }
    }

    val playerLayer = remember(avPlayer) {
        AVPlayerLayer.playerLayerWithPlayer(avPlayer).apply {
            videoGravity = AVLayerVideoGravityResizeAspectFill
        }
    }

    // Handle Looping and Buffering
    DisposableEffect(playerItem, avPlayer) {
        // Loop notification
        val loopObserver = NSNotificationCenter.defaultCenter.addObserverForName(
            name = AVPlayerItemDidPlayToEndTimeNotification,
            `object` = playerItem,
            queue = NSOperationQueue.mainQueue
        ) { _ ->
            avPlayer.seekToTime(kCMTimeZero.readValue())
            if (isPlaying) {
                avPlayer.play()
            }
        }

        // Periodic time observer with GCD dispatch queue
        val timeObserver = avPlayer.addPeriodicTimeObserverForInterval(
            interval = CMTimeMake(value = 1, timescale = 10),
            queue = dispatch_get_main_queue()
        ) { _ ->
            onBufferingStateChanged(false)
        }

        onDispose {
            NSNotificationCenter.defaultCenter.removeObserver(loopObserver)
            avPlayer.removeTimeObserver(timeObserver)
            avPlayer.pause()
        }
    }

    // Handle Play / Pause
    LaunchedEffect(isPlaying) {
        if (isPlaying) {
            avPlayer.play()
        } else {
            avPlayer.pause()
        }
    }

    // Handle Mute
    LaunchedEffect(isMuted) {
        avPlayer.muted = isMuted
    }

    UIKitView(
        factory = {
            val containerView = object : UIView(CGRectMake(0.0, 0.0, 0.0, 0.0)) {
                override fun layoutSubviews() {
                    super.layoutSubviews()
                    CATransaction.begin()
                    CATransaction.setDisableActions(true)
                    playerLayer.frame = bounds
                    CATransaction.commit()
                }
            }
            containerView.layer.addSublayer(playerLayer)
            containerView
        },
        update = { view ->
            CATransaction.begin()
            CATransaction.setDisableActions(true)
            playerLayer.frame = view.bounds
            CATransaction.commit()
            if (isPlaying) {
                avPlayer.play()
            }
        },
        modifier = modifier.fillMaxSize(),
        onRelease = {
            avPlayer.pause()
            playerLayer.removeFromSuperlayer()
        }
    )
}