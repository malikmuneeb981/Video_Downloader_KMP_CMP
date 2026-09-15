package com.cyberarsenals.video.downloader.save.videos.domain.models.apiModels.reels

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ReelsResponseItem(
    val id: Int,
    val category: Int,

    @SerialName("is_ad")
    val isAd: Boolean,

    @SerialName("is_premium")
    val isPremium: Boolean,

    val url: String,

    @SerialName("is_type")
    val type: String,

    @SerialName("created_at")
    val createdAt: String,

    var isLiked: Boolean=false,

    var isBookmarked: Boolean=false
)