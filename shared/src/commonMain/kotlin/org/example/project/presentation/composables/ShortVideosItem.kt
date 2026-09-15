package org.example.project.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.LocalPlatformContext
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import downloaderkmpproductionapp.shared.generated.resources.Res
import downloaderkmpproductionapp.shared.generated.resources.ic_play_short_reels
import org.jetbrains.compose.resources.vectorResource

@Composable
fun ShortVideoItem(url: String,onItemClick:()-> Unit) {

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .width(120.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable { onItemClick()}
            .background(Color(0xFFF3F4F6))
    )
    {

        if (url.isNotBlank()) {
            CoilImageWithShimmer(url)
        } else {
            // SHIMMER OVERLAY FOR BLANK URL (LOADING STATE)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .customShimmer()
            )
        }

        // DARK GRADIENT OVERLAY (for play icon visibility)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.35f)
                        )
                    )
                )
        )

        // PLAY BUTTON
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .background(Color(0xFF1F2937).copy(alpha = 0.4f), CircleShape)
                .padding(all = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                imageVector = vectorResource(Res.drawable.ic_play_short_reels),
                contentDescription = null,
            )
        }
    }

}
@Composable
fun CoilImageWithShimmer(url: String) {
    MediaThumbnailImage(
        model = url,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
}

@Preview
@Composable
private fun ShortVideoItemPrev() {
    ShortVideoItem("", onItemClick = {

    })
}