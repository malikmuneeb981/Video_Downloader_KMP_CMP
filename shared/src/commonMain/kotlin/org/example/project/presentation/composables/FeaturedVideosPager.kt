package com.cyberarsenals.video.downloader.save.videos.presentation.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.cyberarsenals.video.downloader.save.videos.domain.models.apiModels.dailymotion.DailyMotionVideosResponse.Video
import com.translate.speech.to.text.dictionary.instant.voice.translator.presentation.composables.AppText
import downloaderkmpproductionapp.shared.generated.resources.Res
import downloaderkmpproductionapp.shared.generated.resources.ic_play_short_reels
import downloaderkmpproductionapp.shared.generated.resources.nunito_semibold
import downloaderkmpproductionapp.shared.generated.resources.trending
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FeaturedVideosPager(
    category: String,
    items: List<Video>,
    modifier: Modifier = Modifier,
    navigateToWebView:(Video)-> Unit
) {


    val pagerState = rememberPagerState(
        pageCount = { items.size }
    )

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 25.dp),
            pageSpacing = 12.dp,
            modifier = Modifier.fillMaxWidth()
        ) { page ->

            val item = items[page]

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(RoundedCornerShape(20.dp)).clickable(onClick = {
                        navigateToWebView(item)
                    })
            ) {

                AsyncImage(
                    model = item.thumbnail,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.matchParentSize()
                )

                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.65f)
                                )
                            )
                        )
                )

                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .background(
                            Color.White.copy(alpha = 0.35f),
                            CircleShape
                        ).padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        imageVector = vectorResource(Res.drawable.ic_play_short_reels),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(11.dp)
                ) {

                    AppText(
                        text = item.title.toString(),
                        color = Color.White,
                        fontSize = 16.sp,
                        font = Res.font.nunito_semibold,
                        modifier = Modifier.fillMaxWidth(0.4f),
                        textAlign = TextAlign.Start,
                        textLines = 1
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(
                            top = 4.dp
                        )
                    ) {

                        Box(
                            modifier = Modifier
                                .background(
                                    Color.White.copy(alpha = 0.25f),
                                    RoundedCornerShape(50)
                                )
                                .padding(
                                    horizontal = 14.dp,
                                    vertical = 6.dp
                                )
                        ) {
                            AppText(
                                text = item.duration.toString(),
                                color = Color.White,
                                fontSize = 10.sp
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Box(
                            modifier = Modifier
                                .background(
                                    Color.White.copy(alpha = 0.25f),
                                    RoundedCornerShape(50)
                                )
                                .padding(
                                    horizontal = 14.dp,
                                    vertical = 6.dp
                                )
                        ) {
                            AppText(
                                text = category,
                                color = Color.White,
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            repeat(items.size) { index ->

                val selected = pagerState.currentPage == index

                Box(
                    modifier = Modifier
                        .padding(horizontal = 3.dp)
                        .height(8.dp)
                        .width(if (selected) 38.dp else 18.dp)
                        .clip(CircleShape)
                        .background(
                            if (selected)
                                Color(0xFF1E4DFF)
                            else
                                Color.LightGray.copy(alpha = 0.6f)
                        )
                )
            }
        }
    }
}

@Preview
@Composable
private fun FeaturedVideosPagerPrev() {
    FeaturedVideosPager(
        category = stringResource(Res.string.trending),
        listOf(Video()),
        navigateToWebView = {

        }
    )
}