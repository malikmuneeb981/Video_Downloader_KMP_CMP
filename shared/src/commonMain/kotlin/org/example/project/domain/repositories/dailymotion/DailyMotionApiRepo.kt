package com.cyberarsenals.video.downloader.save.videos.domain.repositories.dailymotion

import io.ktor.client.statement.HttpResponse

interface DailyMotionApiRepo {
    suspend fun getVideos(): HttpResponse
    suspend fun getSearchVideos(keyword: String): HttpResponse
}