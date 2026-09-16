package com.cyberarsenals.video.downloader.save.videos.data.reels

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.statement.HttpResponse

import org.example.project.config.AppConfig

class ReelsApiService(val httpClient: HttpClient) {
    val baseUrl = AppConfig.BASE_URL
    val endpoint = "reels/"
    val reelsUrl = "$baseUrl$endpoint"

    suspend fun getReels(): HttpResponse
    {

        return  httpClient.get(reelsUrl){
            headers {
                append("X-Secret-Key", AppConfig.SECRET_KEY)
            }
        }
    }
}