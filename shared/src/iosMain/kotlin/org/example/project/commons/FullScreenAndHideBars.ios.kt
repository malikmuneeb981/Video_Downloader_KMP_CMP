package org.example.project.commons

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import platform.UIKit.UIApplication
import platform.UIKit.UIStatusBarAnimation
import platform.UIKit.setStatusBarHidden

@Composable
actual fun FullScreenAndHideBars() {
    DisposableEffect(Unit) {

        UIApplication.sharedApplication.setStatusBarHidden(true, withAnimation = UIStatusBarAnimation.UIStatusBarAnimationFade)

        onDispose {
            UIApplication.sharedApplication.setStatusBarHidden(false, withAnimation = UIStatusBarAnimation.UIStatusBarAnimationFade)
        }
    }
}