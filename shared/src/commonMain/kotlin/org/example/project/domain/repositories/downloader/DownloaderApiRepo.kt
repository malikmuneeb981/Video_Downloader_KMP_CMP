package com.cyberarsenals.video.downloader.save.videos.domain.repositories.downloader

import io.ktor.client.statement.HttpResponse

interface DownloaderApiRepo {
    suspend fun downloadVideo(vidUrl:String): HttpResponse
}