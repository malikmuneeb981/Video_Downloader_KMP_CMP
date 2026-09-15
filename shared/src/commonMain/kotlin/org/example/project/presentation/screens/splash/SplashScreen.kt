package org.example.project.presentation.screens.splash

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.translate.speech.to.text.dictionary.instant.voice.translator.presentation.composables.AppText
import downloaderkmpproductionapp.shared.generated.resources.Res
import downloaderkmpproductionapp.shared.generated.resources.and_video_player
import downloaderkmpproductionapp.shared.generated.resources.downloader_upper
import downloaderkmpproductionapp.shared.generated.resources.nunito_extrabold
import downloaderkmpproductionapp.shared.generated.resources.social_media
import downloaderkmpproductionapp.shared.generated.resources.splash_bg
import downloaderkmpproductionapp.shared.generated.resources.splash_img
import downloaderkmpproductionapp.shared.generated.resources.video
import org.example.project.navigation.NavRoutes
import org.example.project.presentation.composables.AppMainButton
import org.example.project.presentation.viewModels.DownloaderViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun SplashScreen(navController: NavController, downloaderViewModel: DownloaderViewModel) {

    var progress by remember { mutableFloatStateOf(0f) }
    val isFirstTime by downloaderViewModel.isFirstTime.collectAsStateWithLifecycle(true)
    val textFromIntentForDownloader by downloaderViewModel.extractedTextFromIntent.collectAsStateWithLifecycle()
    var showBtn by remember { mutableStateOf(false) }

    // Animate + Navigate
    LaunchedEffect(Unit) {
        progress = 1f
        downloaderViewModel.checkIsFirstTime()
        downloaderViewModel.checkOutSideDownloadAllowed()
    }

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 2000),
        finishedListener = {
            showBtn = true
        }
    )

    Scaffold(
        containerColor = Color.White
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painter = painterResource(Res.drawable.splash_bg),
                    contentScale = ContentScale.Crop
                )
                .padding(paddingValues)
        ) {

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(Res.drawable.splash_img),
                    contentDescription = null,
                    modifier = Modifier.size(
                        width = 120.dp,
                        height = 125.dp
                    )
                )
                AppText(
                    text = stringResource(Res.string.social_media),
                    font = Res.font.nunito_extrabold,
                    fontSize = 16.sp,
                    color = Color(0xFF003DCE).copy(
                        alpha = 0.70f
                    ), modifier = Modifier.padding(
                        top = 7.dp
                    ),
                    fontWeight = FontWeight.ExtraBold
                )
                AppText(
                    text = stringResource(Res.string.video),
                    font = Res.font.nunito_extrabold,
                    fontSize = 60.sp,
                    color = Color(0xFF011552)
                )
                AppText(
                    text = stringResource(Res.string.downloader_upper),
                    font = Res.font.nunito_extrabold,
                    fontSize = 35.sp,
                    color = Color(0xFF011552)
                )
                AppText(
                    text = stringResource(Res.string.and_video_player),
                    font = Res.font.nunito_extrabold,
                    fontSize = 16.sp,
                    color = Color(0xFF003DCE), modifier = Modifier.padding(
                        top = 5.dp
                    )
                )
            }


            if (showBtn) {
                AppMainButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 25.dp
                        )
                        .padding(
                            bottom = 30.dp
                        )
                        .clickable(indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                            ,onClick = {
                            if (textFromIntentForDownloader.isNotEmpty()) {
                                navController.navigate(NavRoutes.DownloaderHomeScreen.route) {
                                    popUpTo(0) // optional: remove splash from backstack
                                }
                            } else {

                                if (isFirstTime) {
                                    navController.navigate(NavRoutes.LanguageScreen.route) {
                                        popUpTo(0) // optional: remove splash from backstack
                                    }
                                } else {
                                    navController.navigate(NavRoutes.DownloaderHomeScreen.route) {
                                        popUpTo(0) // optional: remove splash from backstack
                                    }
                                }
                            }
                        })
                )
            } else {
                SplashProgress(animatedProgress = animatedProgress.coerceIn(0f, 1f))
            }


        }
    }
}

@Composable
fun SplashProgress(animatedProgress: Float) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 60.dp)
            .padding(top = 30.dp, bottom = 45.dp)
            .height(10.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(30.dp)
            )
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth(animatedProgress)
                .fillMaxHeight()
                .background(Color(0xFF2246F7)
                    ,
                    shape = RoundedCornerShape(30.dp)
                )
        )
    }
}

@Preview
@Composable
private fun SplashScreenPrev() {


}