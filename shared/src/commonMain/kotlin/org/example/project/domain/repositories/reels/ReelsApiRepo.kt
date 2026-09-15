package com.cyberarsenals.video.downloader.save.videos.domain.repositories.reels

import io.ktor.client.statement.HttpResponse

interface ReelsApiRepo {
    suspend fun getReels(): HttpResponse
}