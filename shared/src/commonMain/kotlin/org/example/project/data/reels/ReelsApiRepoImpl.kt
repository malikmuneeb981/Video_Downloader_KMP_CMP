package com.cyberarsenals.video.downloader.save.videos.data.reels

import com.cyberarsenals.video.downloader.save.videos.domain.repositories.reels.ReelsApiRepo
import io.ktor.client.statement.HttpResponse

class ReelsApiRepoImpl(private val reelsApiService: ReelsApiService): ReelsApiRepo {
    override suspend fun getReels(): HttpResponse {
        return reelsApiService.getReels()
    }
}