package com.cyberarsenals.video.downloader.save.videos.data.dailymotion

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse

class DailyMotionApiService(private val httpClient: HttpClient) {


    var BaseUrl = "https://downloaderapi.cyberarsenals.com/"
    var ScecretKey = "XLQwhG7XRutcSt489FdHf8OBlZ5E8TrV80tqffT3dNyQ2AgDzr"
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