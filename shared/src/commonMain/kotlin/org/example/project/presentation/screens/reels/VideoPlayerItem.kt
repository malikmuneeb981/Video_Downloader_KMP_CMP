package org.example.project.presentation.screens.reels

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeOff
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cyberarsenals.video.downloader.save.videos.domain.models.apiModels.reels.ReelsResponseItem
import com.translate.speech.to.text.dictionary.instant.voice.translator.presentation.composables.AppText
import downloaderkmpproductionapp.shared.generated.resources.Res
import downloaderkmpproductionapp.shared.generated.resources.for_you
import downloaderkmpproductionapp.shared.generated.resources.ic_backarrow
import downloaderkmpproductionapp.shared.generated.resources.ic_download_reel
import downloaderkmpproductionapp.shared.generated.resources.ic_heart_reels_filled
import downloaderkmpproductionapp.shared.generated.resources.ic_share_reel
import kotlinx.coroutines.delay
import org.example.project.commons.ReelsView
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun VideoPlayerItem(
    reel: ReelsResponseItem,
    isPlaying: Boolean,
    isLiked: Boolean = false,
    isBookMarked: Boolean = false,
    isMuted: Boolean = true,
    onMutedClick: (Boolean) -> Unit,
    onLikeClick: (ReelsResponseItem, Boolean) -> Unit,
    onShareClick: (ReelsResponseItem) -> Unit,
    onBookMarkClick: (ReelsResponseItem, Boolean) -> Unit,
    downloadClick: (ReelsResponseItem) -> Unit,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showHeart by remember { mutableStateOf(false) }
    var showDownloadStarted by remember { mutableStateOf(false) }
    var isBuffering by remember { mutableStateOf(true) }
    var isPlayingState by remember(isPlaying) { mutableStateOf(isPlaying) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(reel.url) {
                detectTapGestures(
                    onDoubleTap = {
                        onLikeClick(reel, isLiked)
                        showHeart = true
                    },
                    onTap = {
                        isPlayingState = !isPlayingState
                    }
                )
            }
    ) {
        // 🎥 Video Player (Platform native player via expect/actual)
        ReelsView(
            url = reel.url,
            isPlaying = isPlayingState,
            isMuted = isMuted,
            modifier = Modifier.fillMaxSize(),
            onBufferingStateChanged = { buffering ->
                isBuffering = buffering
            }
        )

        // 🔝 Top Bar (Back button + For You text)
        Row(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .padding(horizontal = 26.dp)
                .padding(top = 70.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = vectorResource(Res.drawable.ic_backarrow),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.clickable(onClick = onBackPressed)
            )

            AppText(
                text = stringResource(Res.string.for_you),
                modifier = Modifier.weight(1f),
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }

        // ⏳ Buffering loader
        if (isBuffering) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Color.White
            )
        }

        // ❤️ Double tap animation
        if (showHeart) {
            Text(
                text = "❤️",
                fontSize = 90.sp,
                modifier = Modifier.align(Alignment.Center)
            )

            LaunchedEffect(Unit) {
                delay(700)
                showHeart = false
            }
        }

        // 📥 Download started alert
        if (showDownloadStarted) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(14.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(35.dp),
                    trackColor = Color.White,
                    color = Color(0xFF1E4DFF)
                )
                AppText(
                    text = "Downloading Reel",
                    modifier = Modifier.padding(top = 8.dp),
                    fontWeight = FontWeight.Medium
                )
            }

            LaunchedEffect(Unit) {
                delay(2000)
                showDownloadStarted = false
            }
        }

        // ❤️ Like, Share, Download Buttons
        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 30.dp, end = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = if (isLiked) {
                    vectorResource(Res.drawable.ic_heart_reels_filled)
                } else {
                    Icons.Default.FavoriteBorder
                },
                contentDescription = null,
                tint = if (isLiked) Color.Red else Color.White,
                modifier = Modifier
                    .size(28.dp)
                    .clickable { onLikeClick(reel, isLiked) }
            )

            Icon(
                imageVector = vectorResource(Res.drawable.ic_share_reel),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(28.dp)
                    .clickable { onShareClick(reel) }
            )

            Box(
                modifier = Modifier
                    .background(
                        brush = Brush.verticalGradient(
                            listOf(Color(0xFF2761FB), Color(0xFF2246F7), Color(0xFF0C3AED))
                        ),
                        shape = CircleShape
                    )
                    .border(
                        width = 2.dp,
                        shape = CircleShape,
                        color = Color.White.copy(alpha = 0.47f)
                    )
                    .clip(CircleShape)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = {
                            showDownloadStarted = true
                            downloadClick(reel)
                        }
                    )
                    .padding(all = 16.dp)
            ) {
                Icon(
                    imageVector = vectorResource(Res.drawable.ic_download_reel),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }
        }

        // 🔊 Mute Button
        Icon(
            imageVector = if (isMuted) {
                Icons.AutoMirrored.Default.VolumeOff
            } else {
                Icons.AutoMirrored.Default.VolumeUp
            },
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(bottom = 30.dp, start = 16.dp)
                .size(28.dp)
                .clickable { onMutedClick(!isMuted) }
        )
    }
}
