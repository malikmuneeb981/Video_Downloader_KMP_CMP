package org.example.project.commons

import coil3.ImageLoader
import coil3.PlatformContext
import coil3.network.ktor3.KtorNetworkFetcherFactory
import coil3.request.crossfade
import coil3.video.VideoFrameDecoder

actual fun getPlatformImageLoader(context: PlatformContext): ImageLoader {
    return ImageLoader.Builder(context)
        .components {
            add(KtorNetworkFetcherFactory())
            add(VideoFrameDecoder.Factory())
        }
        .crossfade(true)
        .build()
}
