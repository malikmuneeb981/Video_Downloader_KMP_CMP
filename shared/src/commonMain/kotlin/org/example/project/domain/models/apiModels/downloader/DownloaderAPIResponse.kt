package org.example.project.domain.models.apiModels

import kotlinx.serialization.Serializable


@Serializable
data class DownloaderAPIResponse(

    val status: Boolean? = false,

    val title: String? = "",

    val downloadables: List<VideoDownloaderModel>? = null,

    val image_url: String? = "",

    val platform: String? = ""
)