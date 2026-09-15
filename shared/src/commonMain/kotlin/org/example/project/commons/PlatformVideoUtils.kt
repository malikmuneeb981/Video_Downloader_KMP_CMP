package org.example.project.commons

import androidx.compose.runtime.Composable

interface PlatformVideoUtils {
    fun setScreenBrightness(brightness: Float)
    fun getScreenBrightness(): Float
    fun toggleOrientation(isLandscape: Boolean)
    fun enterPictureInPicture()
}

@Composable
expect fun rememberPlatformVideoUtils(): PlatformVideoUtils
