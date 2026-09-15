package org.example.project.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import coil3.compose.LocalPlatformContext
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import org.example.project.commons.loadVideoThumbnail

@Composable
fun MediaThumbnailImage(
    model: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    contentDescription: String? = null
) {
    val isVideo = remember(model) {
        val lower = model.lowercase()
        lower.endsWith(".mp4") || lower.endsWith(".mov") || lower.endsWith(".m4v") ||
        lower.endsWith(".mkv") || lower.endsWith(".webm") || lower.endsWith(".3gp") ||
        lower.contains("/storage/reels/") || lower.contains("reels")
    }

//    if (isVideo) {
        VideoThumbnailView(
            videoSource = model,
            modifier = modifier,
            contentScale = contentScale,
            contentDescription = contentDescription
        )
//    } else {
//        SubcomposeAsyncImage(
//            model = ImageRequest.Builder(LocalPlatformContext.current)
//                .data(model)
//                .crossfade(true)
//                .build(),
//            contentDescription = contentDescription,
//            modifier = modifier,
//            contentScale = contentScale,
//            loading = {
//                Box(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .customShimmer()
//                )
//            },
//            error = {
//                Box(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .background(Color(0xFFE5E7EB))
//                )
//            }
//        )
//    }
}

@Composable
fun VideoThumbnailView(
    videoSource: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    contentDescription: String? = null
) {
    var thumbnailBitmap by remember(videoSource) { mutableStateOf<ImageBitmap?>(null) }
    var isLoading by remember(videoSource) { mutableStateOf(true) }

    LaunchedEffect(videoSource) {
        if (videoSource.isBlank()) {
            isLoading = false
            return@LaunchedEffect
        }
        isLoading = true
        thumbnailBitmap = loadVideoThumbnail(videoSource)
        isLoading = false
    }

    Box(modifier = modifier) {
        when {
            thumbnailBitmap != null -> {
                Image(
                    bitmap = thumbnailBitmap!!,
                    contentDescription = contentDescription,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = contentScale
                )
            }
            isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .customShimmer()
                )
            }
            else -> {
                SubcomposeAsyncImage(
                    model = ImageRequest.Builder(LocalPlatformContext.current)
                        .data(videoSource)
                        .crossfade(true)
                        .build(),
                    contentDescription = contentDescription,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = contentScale,
                    loading = {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .customShimmer()
                        )
                    },
                    error = {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color(0xFFE5E7EB))
                        )
                    }
                )
            }
        }
    }
}
