package com.cyberarsenals.video.downloader.save.videos.data.reels

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.statement.HttpResponse

class ReelsApiService(val httpClient: HttpClient) {
    val baseUrl = "https://downloaderapi.cyberarsenals.com/"
    val endpoint = "reels/"
    val reelsUrl = "$baseUrl$endpoint"

    suspend fun getReels(): HttpResponse
    {

        return  httpClient.get(reelsUrl){
            headers {
                append("X-Secret-Key", "XLQwhG7XRutcSt489FdHf8OBlZ5E8TrV80tqffT3dNyQ2AgDzr")
            }
        }
    }
}