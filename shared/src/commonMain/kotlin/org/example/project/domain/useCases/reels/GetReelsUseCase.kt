package com.cyberarsenals.video.downloader.save.videos.domain.useCases.reels

import com.cyberarsenals.video.downloader.save.videos.domain.repositories.reels.ReelsApiRepo
import io.ktor.client.statement.HttpResponse

class GetReelsUseCase(private val reelsApiRepo: ReelsApiRepo) {
    suspend operator fun invoke(): HttpResponse = reelsApiRepo.getReels()
}