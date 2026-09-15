package org.example.project.commons

import androidx.compose.runtime.Composable

enum class MediaPermission {
    Images,
    Videos
}

interface MediaPermissionState {
    val isGranted: Boolean
    val shouldShowRationale: Boolean
    fun launchPermissionRequest()
}

@Composable
expect fun rememberMediaPermissionState(
    permissions: List<MediaPermission>,
    onPermissionResult: (Boolean) -> Unit = {}
): MediaPermissionState