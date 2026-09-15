package com.cyberarsenals.video.downloader.save.videos.domain.models.appmodels

import org.jetbrains.compose.resources.DrawableResource

data class GenericDialogData(

    val image: DrawableResource,
    val title: String,
    val subTitle: String,
    val negativeBtnText: String,
    val positiveBtnText: String
)
