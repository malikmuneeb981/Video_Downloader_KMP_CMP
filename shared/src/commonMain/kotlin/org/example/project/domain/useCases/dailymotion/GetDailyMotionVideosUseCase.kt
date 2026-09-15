package com.cyberarsenals.video.downloader.save.videos.domain.useCases.dailymotion

import com.cyberarsenals.video.downloader.save.videos.domain.repositories.dailymotion.DailyMotionApiRepo
import io.ktor.client.statement.HttpResponse

class GetDailyMotionVideosUseCase(private val dailyMotionApiRepo: DailyMotionApiRepo) {
    suspend operator fun invoke(): HttpResponse = dailyMotionApiRepo.getVideos()
}