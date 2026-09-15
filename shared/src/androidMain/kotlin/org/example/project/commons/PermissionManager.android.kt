package org.example.project.commons

import android.Manifest
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

class AndroidMediaPermissionState(
    override val isGranted: Boolean,
    override val shouldShowRationale: Boolean,
    private val onLaunch: () -> Unit
) : MediaPermissionState {
    override fun launchPermissionRequest() {
        onLaunch()
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
actual fun rememberMediaPermissionState(
    permissions: List<MediaPermission>,
    onPermissionResult: (Boolean) -> Unit
): MediaPermissionState {
    val listOfPermissions = remember(permissions) {
        val list = mutableListOf<String>()
        if (MediaPermission.Images in permissions) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                list.add(Manifest.permission.READ_MEDIA_IMAGES)
            } else {
                list.add(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
        if (MediaPermission.Videos in permissions) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                list.add(Manifest.permission.READ_MEDIA_VIDEO)
            } else {
                list.add(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
        list.distinct()
    }

    val permissionState = rememberMultiplePermissionsState(listOfPermissions) {
        onPermissionResult(it.values.isNotEmpty() && it.values.all { granted -> granted })
    }

    return remember(permissionState.allPermissionsGranted, permissionState.shouldShowRationale) {
        AndroidMediaPermissionState(
            isGranted = permissionState.allPermissionsGranted,
            shouldShowRationale = permissionState.shouldShowRationale,
            onLaunch = {
                permissionState.launchMultiplePermissionRequest()
            }
        )
    }
}
