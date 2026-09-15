package org.example.project.data

import io.ktor.client.HttpClient
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.parameters

class DownloaderApiService(val httpClient: HttpClient) {


    var BaseUrl = "https://downloaderapi.cyberarsenals.com/"
    var ScecretKey = "XLQwhG7XRutcSt489FdHf8OBlZ5E8TrV80tqffT3dNyQ2AgDzr"
    val endpoint = "endpointchecker/"
    val downloaderUrl = "${BaseUrl}$endpoint"

    suspend fun downloadVideo(vidUrl:String): HttpResponse
    {


        return  httpClient.post(downloaderUrl) {
            headers {
                append("X-Secret-Key", ScecretKey)
            }

            setBody(
                FormDataContent(
                    parameters {
                        append("url", vidUrl)
                    }
                )
            )
        }


    }

}