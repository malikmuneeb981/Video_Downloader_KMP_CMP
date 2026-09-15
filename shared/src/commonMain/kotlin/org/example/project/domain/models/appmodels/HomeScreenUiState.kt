package com.cyberarsenals.video.downloader.save.videos.domain.models.appmodels

import org.example.project.domain.models.apiModels.DownloaderAPIResponse

data class HomeScreenUiState(
    val downloaderAPIResponse: DownloaderAPIResponse?=null,
    val isLoading : Boolean=false,
    val lastRequestedUrl: String = "",
    val showBtmSheet: Boolean = false,
)
