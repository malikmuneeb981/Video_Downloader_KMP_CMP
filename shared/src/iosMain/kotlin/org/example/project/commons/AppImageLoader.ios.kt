package org.example.project.commons

import coil3.ImageLoader
import coil3.PlatformContext
import coil3.network.ktor3.KtorNetworkFetcherFactory
import coil3.request.crossfade
import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin

import coil3.annotation.ExperimentalCoilApi

@OptIn(ExperimentalCoilApi::class)
actual fun getPlatformImageLoader(context: PlatformContext): ImageLoader {
    return ImageLoader.Builder(context)
        .components {
            add(KtorNetworkFetcherFactory(HttpClient(Darwin)))
        }
        .crossfade(true)
        .build()
}
