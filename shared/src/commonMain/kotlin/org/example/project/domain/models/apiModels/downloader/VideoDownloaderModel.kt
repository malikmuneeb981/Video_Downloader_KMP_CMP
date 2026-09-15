package org.example.project.domain.models.apiModels

import kotlinx.serialization.Serializable

@Serializable
data class VideoDownloaderModel(

    val quality: String? = "",

    val url: String? = "",

)