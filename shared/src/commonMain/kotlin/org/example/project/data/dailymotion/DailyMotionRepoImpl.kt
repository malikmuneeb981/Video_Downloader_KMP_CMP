package com.cyberarsenals.video.downloader.save.videos.data.dailymotion

import com.cyberarsenals.video.downloader.save.videos.domain.repositories.dailymotion.DailyMotionApiRepo
import com.cyberarsenals.video.downloader.save.videos.domain.repositories.downloader.DownloaderApiRepo
import io.ktor.client.statement.HttpResponse
import org.example.project.data.DownloaderApiService

class DailyMotionRepoImpl(private val dailyMotionApiService: DailyMotionApiService): DailyMotionApiRepo {
    override suspend fun getVideos(
    ): HttpResponse {
        return dailyMotionApiService.getVideos()
    }

    override suspend fun getSearchVideos(keyword: String): HttpResponse {
        return dailyMotionApiService.getSearchVideos(keyword = keyword)
    }
}