package org.example.project.data

import io.ktor.client.statement.HttpResponse
import com.cyberarsenals.video.downloader.save.videos.domain.repositories.downloader.DownloaderApiRepo

class DownloaderRepoImpl(private val downloaderApiService: DownloaderApiService): DownloaderApiRepo {
    override suspend fun downloadVideo(
        vidUrl: String
    ): HttpResponse {

        return downloaderApiService.downloadVideo(vidUrl = vidUrl)
    }
}