package com.cyberarsenals.video.downloader.save.videos.domain.useCases.downloader

import com.cyberarsenals.video.downloader.save.videos.domain.repositories.downloader.DownloaderApiRepo
import io.ktor.client.statement.HttpResponse

class DownloadVideoUseCase(private val downloaderApiRepo: DownloaderApiRepo) {
    suspend operator fun invoke(vidUrl: String): HttpResponse = downloaderApiRepo.downloadVideo(vidUrl = vidUrl)
}