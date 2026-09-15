package com.cyberarsenals.video.downloader.save.videos.domain.models.apiModels.dailymotion

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DailyMotionSearchResponse(
    @SerialName("success")
    val success: Boolean = false,

    @SerialName("keyword")
    val keyword: String? = null,

    @SerialName("total_videos")
    val totalVideos: Int = 0,

    @SerialName("videos")
    val videos: List<DailyMotionVideosResponse.Video> = emptyList()
) {


}