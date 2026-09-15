package org.example.project.commons

import android.app.Activity
import android.app.PictureInPictureParams
import android.content.pm.ActivityInfo
import android.os.Build
import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

class AndroidPlatformVideoUtils(private val activity: Activity?) : PlatformVideoUtils {
    override fun setScreenBrightness(brightness: Float) {
        activity?.let {
            val layoutParams = it.window.attributes
            layoutParams.screenBrightness = brightness.coerceIn(0.01f, 1f)
            it.window.attributes = layoutParams
        }
    }

    override fun getScreenBrightness(): Float {
        return activity?.window?.attributes?.screenBrightness?.takeIf { it >= 0 }
            ?: try {
                val contentResolver = activity?.contentResolver
                if (contentResolver != null) {
                    Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS) / 255f
                } else {
                    0.5f
                }
            } catch (e: Exception) {
                0.5f
            }
    }

    override fun toggleOrientation(isLandscape: Boolean) {
        activity?.requestedOrientation = if (isLandscape) {
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        } else {
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
    }

    override fun enterPictureInPicture() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            activity?.enterPictureInPictureMode(PictureInPictureParams.Builder().build())
        }
    }
}

@Composable
actual fun rememberPlatformVideoUtils(): PlatformVideoUtils {
    val context = LocalContext.current
    return remember(context) { AndroidPlatformVideoUtils(context as? Activity) }
}
