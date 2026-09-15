package com.cyberarsenals.video.downloader.save.videos.domain.models.apiModels.dailymotion

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class DailyMotionVideosResponse(
    @SerialName("success")
    val success: Boolean? = null,

    @SerialName("total_categories")
    val totalCategories: Int? = null,

    @SerialName("categories")
    val categories: List<Category>? = null
) {
    @Serializable
    data class Category(
        @SerialName("id")
        val id: Int? = null,

        @SerialName("name")
        val name: String? = null,

        @SerialName("slug")
        val slug: String? = null,

        @SerialName("order")
        val order: Int? = null,

        @SerialName("videos")
        val videos: List<Video>? = null
    )
    @Serializable
    data class Video(
        @SerialName("id")
        val id: String? = null,

        @SerialName("title")
        val title: String? = null,

        @SerialName("thumbnail")
        val thumbnail: String? = null,

        @SerialName("duration")
        val duration: Int? = null,

        @SerialName("views")
        val views: Int? = null,

        @SerialName("video_url")
        val videoUrl: String? = null
    )
}