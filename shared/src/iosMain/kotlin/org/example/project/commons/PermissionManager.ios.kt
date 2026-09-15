package org.example.project.commons

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import platform.Photos.PHAccessLevelReadWrite
import platform.Photos.PHAuthorizationStatusAuthorized
import platform.Photos.PHAuthorizationStatusLimited
import platform.Photos.PHAuthorizationStatusNotDetermined
import platform.Photos.PHPhotoLibrary

class IosMediaPermissionState(
    override val isGranted: Boolean,
    override val shouldShowRationale: Boolean,
    private val onLaunch: () -> Unit
) : MediaPermissionState {
    override fun launchPermissionRequest() {
        onLaunch()
    }
}

@Composable
actual fun rememberMediaPermissionState(
    permissions: List<MediaPermission>,
    onPermissionResult: (Boolean) -> Unit
): MediaPermissionState {
    var status by remember {
        mutableStateOf(
            PHPhotoLibrary.authorizationStatusForAccessLevel(PHAccessLevelReadWrite)
        )
    }

    val isGranted = status == PHAuthorizationStatusAuthorized || status == PHAuthorizationStatusLimited

    val onLaunch: () -> Unit = {
        if (status == PHAuthorizationStatusNotDetermined) {
            PHPhotoLibrary.requestAuthorizationForAccessLevel(PHAccessLevelReadWrite) { newStatus ->
                status = newStatus
                val granted = newStatus == PHAuthorizationStatusAuthorized || newStatus == PHAuthorizationStatusLimited
                onPermissionResult(granted)
            }
        } else {
            val currentStatus = PHPhotoLibrary.authorizationStatusForAccessLevel(PHAccessLevelReadWrite)
            status = currentStatus
            val granted = currentStatus == PHAuthorizationStatusAuthorized || currentStatus == PHAuthorizationStatusLimited
            onPermissionResult(granted)
        }
    }

    return remember(isGranted, status) {
        IosMediaPermissionState(
            isGranted = isGranted,
            shouldShowRationale = false,
            onLaunch = onLaunch
        )
    }
}
