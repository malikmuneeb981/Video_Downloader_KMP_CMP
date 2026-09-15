package com.cyberarsenals.video.downloader.save.videos.domain.models.appmodels

import org.jetbrains.compose.resources.DrawableResource

data class PermissionBtmSheetModel(
    val image: DrawableResource,
    val permissionTitle: String,
    val permissionText: String,
)
