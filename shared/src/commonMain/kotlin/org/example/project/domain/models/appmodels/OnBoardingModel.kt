package com.cyberarsenals.video.downloader.save.videos.domain.models.appmodels

import org.jetbrains.compose.resources.DrawableResource


data class OnBoardingModel(
    val index: Int,
    val text1: String,
    val text2: String,
    val text3: String,
    val image: DrawableResource,
    val onBoardingBtmCardItems: List<OnBoardingBtmCardItems>
)

data class OnBoardingBtmCardItems(
    val image: DrawableResource,
    val text1: String,
    val text2: String
)
