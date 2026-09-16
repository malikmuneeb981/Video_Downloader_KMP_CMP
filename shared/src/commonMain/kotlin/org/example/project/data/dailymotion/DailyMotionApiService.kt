package com.cyberarsenals.video.downloader.save.videos.data.dailymotion

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse

import org.example.project.config.AppConfig

class DailyMotionApiService(private val httpClient: HttpClient) {

    var BaseUrl = AppConfig.BASE_URL
    var ScecretKey = AppConfig.SECRET_KEY
    val endPoint = "dailymotionvideos/discover/"
    val endPointSearch = "dailymotionvideos/search/"
    suspend fun getVideos(): HttpResponse
    {
        return  httpClient.get("${BaseUrl}$endPoint"){
            headers {
                append("X-Secret-Key", ScecretKey)
            }
        }.body()
    }
    suspend fun getSearchVideos(keyword: String): HttpResponse
    {
        return  httpClient.get("${BaseUrl}$endPointSearch"){
            headers {
                append("X-Secret-Key", ScecretKey)
            }
            parameter("keyword", keyword)
        }.body()
    }
}