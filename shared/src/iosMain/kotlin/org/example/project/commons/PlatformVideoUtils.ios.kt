package org.example.project.commons

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import platform.Foundation.NSNumber
import platform.Foundation.setValue
import platform.UIKit.UIApplication
import platform.UIKit.UIDevice
import platform.UIKit.UIInterfaceOrientationLandscapeRight
import platform.UIKit.UIInterfaceOrientationMaskLandscapeRight
import platform.UIKit.UIInterfaceOrientationMaskPortrait
import platform.UIKit.UIInterfaceOrientationPortrait
import platform.UIKit.UIScreen
import platform.UIKit.UIViewController
import platform.UIKit.UIWindowScene
import platform.UIKit.UIWindowSceneGeometryPreferencesIOS

class IosPlatformVideoUtils : PlatformVideoUtils {
    override fun setScreenBrightness(brightness: Float) {
        UIScreen.mainScreen.brightness = brightness.toDouble().coerceIn(0.01, 1.0)
    }

    override fun getScreenBrightness(): Float {
        return UIScreen.mainScreen.brightness.toFloat()
    }

    override fun toggleOrientation(isLandscape: Boolean) {
        val targetMask = if (isLandscape) {
            UIInterfaceOrientationMaskPortrait
        } else {
            UIInterfaceOrientationMaskLandscapeRight
        }

        val targetOrientation = if (isLandscape) {
            UIInterfaceOrientationPortrait
        } else {
            UIInterfaceOrientationLandscapeRight
        }

        try {
            val windowScene = UIApplication.sharedApplication.keyWindow?.windowScene
            if (windowScene != null) {
                val geometryPreferences = UIWindowSceneGeometryPreferencesIOS(targetMask)
                windowScene.requestGeometryUpdateWithPreferences(geometryPreferences) { _ ->
                    setLegacyOrientation(targetOrientation)
                }
            } else {
                setLegacyOrientation(targetOrientation)
            }
        } catch (_: Exception) {
            setLegacyOrientation(targetOrientation)
        }
    }

    private fun setLegacyOrientation(orientation: Long) {
        try {
            UIDevice.currentDevice.setValue(NSNumber(long = orientation), forKey = "orientation")
        } catch (_: Exception) {}
    }

    override fun enterPictureInPicture() {
    }
}

@Composable
actual fun rememberPlatformVideoUtils(): PlatformVideoUtils {
    return remember { IosPlatformVideoUtils() }
}
