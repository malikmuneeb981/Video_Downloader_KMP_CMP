package com.cyberarsenals.video.downloader.save.videos.domain.models.appmodels

data class HomeHotFeaturesModel(
    val image: Int,
    val text: String,
    val isHot: Boolean = false,
    val applyTint : Boolean = true
)
