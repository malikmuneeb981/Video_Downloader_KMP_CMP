package org.example.project.presentation.screens.onBoarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.cyberarsenals.video.downloader.save.videos.domain.models.appmodels.OnBoardingBtmCardItems
import com.cyberarsenals.video.downloader.save.videos.domain.models.appmodels.OnBoardingModel
import com.translate.speech.to.text.dictionary.instant.voice.translator.presentation.composables.AppText
import downloaderkmpproductionapp.shared.generated.resources.Res
import downloaderkmpproductionapp.shared.generated.resources.built_in
import downloaderkmpproductionapp.shared.generated.resources.download_videos_from_favorite
import downloaderkmpproductionapp.shared.generated.resources.fast_and_easy
import downloaderkmpproductionapp.shared.generated.resources.get_started
import downloaderkmpproductionapp.shared.generated.resources.ic_onboarding_card1_img1
import downloaderkmpproductionapp.shared.generated.resources.ic_onboarding_card1_img2
import downloaderkmpproductionapp.shared.generated.resources.ic_onboarding_card1_img3
import downloaderkmpproductionapp.shared.generated.resources.ic_onboarding_card2_img1
import downloaderkmpproductionapp.shared.generated.resources.ic_onboarding_card2_img2
import downloaderkmpproductionapp.shared.generated.resources.ic_onboarding_card2_img3
import downloaderkmpproductionapp.shared.generated.resources.ic_onboarding_card3_img1
import downloaderkmpproductionapp.shared.generated.resources.ic_onboarding_card3_img2
import downloaderkmpproductionapp.shared.generated.resources.ic_onboarding_card3_img3
import downloaderkmpproductionapp.shared.generated.resources.multi_platform_support
import downloaderkmpproductionapp.shared.generated.resources.next
import downloaderkmpproductionapp.shared.generated.resources.nunito_bold
import downloaderkmpproductionapp.shared.generated.resources.nunito_extrabold
import downloaderkmpproductionapp.shared.generated.resources.nunito_semibold
import downloaderkmpproductionapp.shared.generated.resources.on_boarding_img1
import downloaderkmpproductionapp.shared.generated.resources.on_boarding_img2
import downloaderkmpproductionapp.shared.generated.resources.on_boarding_img3
import downloaderkmpproductionapp.shared.generated.resources.paste_any_video_url
import downloaderkmpproductionapp.shared.generated.resources.play_downloaded_videos_high_quality
import downloaderkmpproductionapp.shared.generated.resources.quick_simple_high_quality
import downloaderkmpproductionapp.shared.generated.resources.save_and_repost_whatsapp
import downloaderkmpproductionapp.shared.generated.resources.social_media
import downloaderkmpproductionapp.shared.generated.resources.splash_img
import downloaderkmpproductionapp.shared.generated.resources.status_saver_upper
import downloaderkmpproductionapp.shared.generated.resources.supports_instagram_facebook_tiktok
import downloaderkmpproductionapp.shared.generated.resources.video_downloader_upper
import downloaderkmpproductionapp.shared.generated.resources.whatsapp_upper
import org.example.project.navigation.NavRoutes
import org.example.project.presentation.composables.AppMainButton
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun OnBoardingScreen(navController: NavController) {
    val pagesList = listOf(
        OnBoardingModel(
            index = 0,
            text1 = stringResource(Res.string.social_media),
            text2 = stringResource(Res.string.video_downloader_upper),
            text3 = stringResource(Res.string.download_videos_from_favorite),
            image = Res.drawable.on_boarding_img1,
            onBoardingBtmCardItems = listOf(OnBoardingBtmCardItems(
                image = Res.drawable.ic_onboarding_card1_img1,
                text1 = "Paste & Download",
                text2 = stringResource(Res.string.paste_any_video_url)
            ),OnBoardingBtmCardItems(
                image = Res.drawable.ic_onboarding_card1_img2,
                text1 = stringResource(Res.string.multi_platform_support),
                text2 = stringResource(Res.string.supports_instagram_facebook_tiktok)
            ),OnBoardingBtmCardItems(
                image = Res.drawable.ic_onboarding_card1_img3,
                text1 = stringResource(Res.string.fast_and_easy),
                text2 = stringResource(Res.string.quick_simple_high_quality)
            ))
        ), OnBoardingModel(
            index = 1,
            text1 = stringResource(Res.string.built_in),
            text2 = "VIDEO PLAYER",
            text3 = stringResource(Res.string.play_downloaded_videos_high_quality),
            image = Res.drawable.on_boarding_img2,
            onBoardingBtmCardItems = listOf(OnBoardingBtmCardItems(
                image = Res.drawable.ic_onboarding_card2_img1,
                text1 = "Paste & Download",
                text2 = stringResource(Res.string.paste_any_video_url)
            ),OnBoardingBtmCardItems(
                image = Res.drawable.ic_onboarding_card2_img2,
                text1 = stringResource(Res.string.multi_platform_support),
                text2 = stringResource(Res.string.supports_instagram_facebook_tiktok)
            ),OnBoardingBtmCardItems(
                image = Res.drawable.ic_onboarding_card2_img3,
                text1 = stringResource(Res.string.fast_and_easy),
                text2 = stringResource(Res.string.quick_simple_high_quality)
            ))
        ), OnBoardingModel(
            index = 2,
            text1 = stringResource(Res.string.whatsapp_upper),
            text2 = stringResource(Res.string.status_saver_upper),
            text3 = stringResource(Res.string.save_and_repost_whatsapp),
            image = Res.drawable.on_boarding_img3,
            onBoardingBtmCardItems = listOf(OnBoardingBtmCardItems(
                image = Res.drawable.ic_onboarding_card3_img1,
                text1 = "Paste & Download",
                text2 = stringResource(Res.string.paste_any_video_url)
            ),OnBoardingBtmCardItems(
                image = Res.drawable.ic_onboarding_card3_img2,
                text1 = stringResource(Res.string.multi_platform_support),
                text2 = stringResource(Res.string.supports_instagram_facebook_tiktok)
            ),OnBoardingBtmCardItems(
                image = Res.drawable.ic_onboarding_card3_img3,
                text1 = stringResource(Res.string.fast_and_easy),
                text2 = stringResource(Res.string.quick_simple_high_quality)
            ))
        ),
    )
    var currentIndex by remember { mutableIntStateOf(0) }

    val isLastItem by remember {
        derivedStateOf {
            currentIndex == pagesList.lastIndex
        }
    }

    val currentPage by remember {
        derivedStateOf {
            pagesList[currentIndex]
        }
    }
    Scaffold(containerColor = Color.White) {

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {

            OnBoardingItem(modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
                currentPage = currentPage
            )
            AppMainButton(
                text = if (isLastItem) stringResource(Res.string.get_started) else stringResource(Res.string.next),
                showArrow = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }, onClick = {
                            if (isLastItem) {
                                navController.navigate(NavRoutes.DownloaderHomeScreen.route)
                            } else {
                                currentIndex++
                            }
                        })
                    .padding(
                        bottom = 20.dp
                    )
                    .padding(
                        horizontal = 16.dp
                    ),

            )




        }

    }

}

@Composable
fun OnBoardingItem(modifier: Modifier= Modifier,
                   currentPage: OnBoardingModel,) {
    Column(modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,) {

       Image(painter = painterResource(Res.drawable.splash_img),
           contentDescription = null, modifier = Modifier.padding(
               top = 10.dp
           ))

        AppText(text = currentPage.text1,
            color = Color.Black,
            font = Res.font.nunito_bold,
            fontSize = 20.sp, modifier = Modifier.padding(
                top = 8.dp
            ))

        AppText(text = currentPage.text2,
            color = Color(0xFF1149F6),
            font = Res.font.nunito_extrabold,
            fontSize = 24.sp, modifier = Modifier.padding(
                top = 4.dp
            ))
        Box(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .padding(
                bottom = 30.dp
            )) {

            Image(painter = painterResource(
                Res.drawable.on_boarding_img1
            ), contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .matchParentSize()
                    .padding(
                        top = 25.dp
                    ))
            AppText(text = currentPage.text3,
                color = Color(0xFF1F2937).copy(
                    alpha = 0.7f
                ),
                fontSize = 13.sp, modifier = Modifier
                    .padding(
                        top = 4.dp
                    )
                    .padding(
                        horizontal = 50.dp
                    ))
            OnBoardingCard(modifier = Modifier.align(alignment = Alignment.BottomCenter),
                currentPage = currentPage)
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(vertical = 14.dp)
        ) {
            repeat(3) { index ->
                val isSelected = currentPage.index == index
                Box(
                    modifier = Modifier
                        .width(if (isSelected) 74.dp else 21.dp)
                        .height(if (isSelected) 8.dp else 8.dp)
                        .clip(CircleShape)
                        .background(
                            if (isSelected) Color(0xFF003BEE)
                            else Color(0xFF003BEE).copy(
                                alpha =
                                    0.15f
                            )
                        )
                )
            }
        }



    }
}

@Composable
fun OnBoardingCard(modifier: Modifier= Modifier,
                   currentPage: OnBoardingModel) {
    Column(modifier = modifier
        .fillMaxWidth()
        .padding(
            horizontal = 16.dp
        )
        .background(
            color = Color(0xFFF9FAFB),
            shape = RoundedCornerShape(12.dp)
        ).padding(
            12.dp
        )) {

        Row(modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier.background(
                color = Color(0xFFEDF2FD),
                shape = RoundedCornerShape(7.dp)
            ).padding(
                8.dp
            )) {
                Image(imageVector = vectorResource(
                    currentPage.onBoardingBtmCardItems[0].image
                ),contentDescription = null)
            }
            Column(modifier = Modifier.weight(1f).padding(
                start = 17.dp
            )) {
                AppText(text = currentPage.onBoardingBtmCardItems[0].text1,
                    fontSize = 16.sp,
                    font = Res.font.nunito_semibold,
                    color = Color(0xFF1F2937))
                AppText(text = currentPage.onBoardingBtmCardItems[0].text2,
                    fontSize = 11.sp,
                    color = Color(0xFF1F2937).copy(
                        alpha = 0.7f
                    ))
            }
        }
        Spacer(modifier = Modifier.fillMaxWidth().padding(
            vertical = 10.dp
        ).height(
            1.dp
        ).background(color = Color(0xFFECECEC)))
        Row(modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier.background(
                color = Color(0xFFEDF2FD),
                shape = RoundedCornerShape(7.dp)
            ).padding(
                8.dp
            )) {
                Image(imageVector = vectorResource(
                    currentPage.onBoardingBtmCardItems[1].image
                ),contentDescription = null)
            }
            Column(modifier = Modifier.weight(1f).padding(
                start = 17.dp
            )) {
                AppText(text = currentPage.onBoardingBtmCardItems[1].text1,
                    fontSize = 16.sp,
                    font = Res.font.nunito_semibold,
                    color = Color(0xFF1F2937))
                AppText(text = currentPage.onBoardingBtmCardItems[1].text2,
                    fontSize = 11.sp,
                    color = Color(0xFF1F2937).copy(
                        alpha = 0.7f
                    ))
            }
        }
        Spacer(modifier = Modifier.fillMaxWidth().padding(
            vertical = 10.dp
        ).height(
            1.dp
        ).background(color = Color(0xFFECECEC)))
        Row(modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier.background(
                color = Color(0xFFEDF2FD),
                shape = RoundedCornerShape(7.dp)
            ).padding(
                8.dp
            )) {
                Image(imageVector = vectorResource(
                    currentPage.onBoardingBtmCardItems[2].image
                ),contentDescription = null)
            }
            Column(modifier = Modifier.weight(1f).padding(
                start = 17.dp
            )) {
                AppText(text = currentPage.onBoardingBtmCardItems[2].text1,
                    fontSize = 16.sp,
                    font = Res.font.nunito_semibold,
                    color = Color(0xFF1F2937))
                AppText(text = currentPage.onBoardingBtmCardItems[2].text2,
                    fontSize = 11.sp,
                    color = Color(0xFF1F2937).copy(
                        alpha = 0.7f
                    ))
            }
        }

    }
}


@Preview
@Composable
private fun OnBoardingScreenPrev() {
    OnBoardingScreen(rememberNavController())
}