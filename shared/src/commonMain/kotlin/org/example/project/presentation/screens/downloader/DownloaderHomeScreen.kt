package org.example.project.presentation.screens.downloader

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.cyberarsenals.video.downloader.save.videos.domain.models.appmodels.SocialApps
import com.cyberarsenals.video.downloader.save.videos.presentation.composables.HomeTopBar
import com.translate.speech.to.text.dictionary.instant.voice.translatoRes.presentation.composables.BottomNav
import com.translate.speech.to.text.dictionary.instant.voice.translator.presentation.composables.AppText
import downloaderkmpproductionapp.shared.generated.resources.Res
import downloaderkmpproductionapp.shared.generated.resources.ic_down_arrow
import downloaderkmpproductionapp.shared.generated.resources.ic_fb_social
import downloaderkmpproductionapp.shared.generated.resources.ic_ig_social
import downloaderkmpproductionapp.shared.generated.resources.ic_link
import downloaderkmpproductionapp.shared.generated.resources.ic_lk_social
import downloaderkmpproductionapp.shared.generated.resources.ic_onboarding_card1_img1
import downloaderkmpproductionapp.shared.generated.resources.ic_pt_social
import downloaderkmpproductionapp.shared.generated.resources.ic_right_arrow_view_all
import downloaderkmpproductionapp.shared.generated.resources.ic_th_social
import downloaderkmpproductionapp.shared.generated.resources.ic_tt_social
import downloaderkmpproductionapp.shared.generated.resources.ic_wa_social
import downloaderkmpproductionapp.shared.generated.resources.ic_wb_social
import downloaderkmpproductionapp.shared.generated.resources.less
import downloaderkmpproductionapp.shared.generated.resources.nunito_bold
import downloaderkmpproductionapp.shared.generated.resources.nunito_medium
import downloaderkmpproductionapp.shared.generated.resources.nunito_semibold
import downloaderkmpproductionapp.shared.generated.resources.paste_video_url
import downloaderkmpproductionapp.shared.generated.resources.short_videos
import downloaderkmpproductionapp.shared.generated.resources.top_supported_platforms
import downloaderkmpproductionapp.shared.generated.resources.view_all
import downloaderkmpproductionapp.shared.generated.resources.we_support_many_platforms
import org.example.project.navigation.NavRoutes
import org.example.project.presentation.btmSheets.DownloadableBtmSheet
import org.example.project.presentation.composables.AppMainButton
import org.example.project.presentation.composables.ShortVideoItem
import org.example.project.presentation.composables.SocialAppItem
import org.example.project.presentation.viewModels.DownloaderViewModel
import org.example.project.presentation.viewModels.HomeScreenViewModel
import org.example.project.presentation.viewModels.ReelsViewModel
import org.example.project.utils.Baseresponse
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun DownloaderHomeScreen(
    downloaderViewModel: DownloaderViewModel,
    homeScreenViewModel: HomeScreenViewModel,
    navController: NavHostController,
    reelsViewModel: ReelsViewModel
) {
    val downloaderTAG = "Downloader Screen"
    val reels by reelsViewModel.reelsResponse.collectAsStateWithLifecycle()
    var showMore by remember { mutableStateOf(false) }
    val downloaderUiState by homeScreenViewModel.uiState.collectAsStateWithLifecycle()
    var link by rememberSaveable{ mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val snackBarState = remember { SnackbarHostState() }
    var endPoint = homeScreenViewModel.checkIfEndPointIsValid(link)
    val placeHolder = ""
    var isFocused by remember { mutableStateOf(false) }
  //  val downloadManager: DownloadManager = getKoin().get()
    val focusManager = LocalFocusManager.current
    val textFromIntentForDownloader by downloaderViewModel.extractedTextFromIntent.collectAsStateWithLifecycle()
   // val activity = LocalActivity.current as Activity
  //  var premiumDialog by remember { mutableStateOf(false) }
    if (textFromIntentForDownloader.isNotEmpty()){
        link = textFromIntentForDownloader
        endPoint = true
        downloaderViewModel.setExtractedTextFromIntent("")
    }
    val socialsListRow1 = listOf(
        SocialApps(image = Res.drawable.ic_wa_social, "WA"),
        SocialApps(image = Res.drawable.ic_wb_social, "WB"),
        SocialApps(image = Res.drawable.ic_fb_social, "FB"),
        SocialApps(image = Res.drawable.ic_ig_social, "IG"),
    )
    val socialsListRow2 = listOf(
        SocialApps(image = Res.drawable.ic_tt_social, "TT"),
        SocialApps(image = Res.drawable.ic_th_social, "TH"),
        SocialApps(image = Res.drawable.ic_lk_social, "LK"),
        SocialApps(image = Res.drawable.ic_pt_social, "PT"),

        )
    if (downloaderUiState.showBtmSheet){
        DownloadableBtmSheet(downloaderUiState.downloaderAPIResponse, dismiss = {
            homeScreenViewModel.dismissBtmSheet()
        }, downloadClick = { response ->
            downloaderViewModel.videoToDownloadResponse = response
          //  premiumDialog = true
            downloaderViewModel.videoToDownloadResponse?.url?.let { url->
                val downloadId = downloaderViewModel.downloadFile(videolink = url)
                downloaderViewModel.updateProgress(downloadId, 0)
                downloaderViewModel.addDownloadingVidThumbnail(downloadId,url)
                navController.navigate(NavRoutes.DownloadsScreen.route)
            }
        }, watchClick = {
//            downloadableBtmSheet = false
//            downloaderViewModel.videoToPlayUrl = it.url
//            downloaderViewModel.videoToPlayFile = null
//            downloaderViewModel.videoToPlayUri =null
//            navController.navigate(NavRoutes.VideoPlayerScreen.route)
        })
    }
//    if (premiumDialog){
//        VerticalBtnsGenericDialog(
//            dialogData = GenericDialogData(image = R.drawable.ic_premium_cam_crown,
//                title = stringResource(R.string.download_the_video),
//                subTitle = stringResource(R.string.watch_ad_to_download),
//                negativeBtnText = stringResource(R.string.go_to_premium),
//                positiveBtnText = stringResource(R.string.watch_ad),
//            ), onDismiss = {
//                premiumDialog = false
//            }, onPositiveBtnClick = {
//                premiumDialog = false
//                RewardedInterstitialAdManager.loadAndShowAd(activity = activity,
//                    unitId = adsConfig.RewardedIntestritialId, onAdDismissed = {
//                        downloadableBtmSheet = false
//                        downloaderViewModel.videoToDownloadResponse?.url?.let { url->
//                            val downloadId = downloaderViewModel.downloadFile(videolink = url,
//                                downloadManager = downloadManager)
//                            downloaderViewModel.updateProgress(downloadId, 0)
//                            downloaderViewModel.addDownloadingVidThumbnail(downloadId,url)
//                            navController.navigate(NavRoutes.DownloadsScreen.route)
//                        }
//                    }, onAdFailedToShow = {
//
//                    })
//            }, onNegativeBtnClick = {
//                premiumDialog = false
//                navController.navigate(NavRoutes.PremiumScreen.route)
//            }
//        )
//    }

    Scaffold(topBar = {
        Column(modifier = Modifier.fillMaxWidth()) {
            HomeTopBar(downloadBtnClick = {
                navController.navigate(NavRoutes.DownloadsScreen.route)
            })
        }
    }, bottomBar = {
        BottomNav(navController = navController)
    }, snackbarHost = {
        SnackbarHost(snackBarState)
    }, content = { paddingValues ->

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(
                top = paddingValues.calculateTopPadding(),
                bottom = (paddingValues.calculateBottomPadding() - 19.dp).coerceAtLeast(0.dp)
            ).verticalScroll(
                rememberScrollState()
            )) {

            Column(modifier = Modifier.fillMaxWidth().padding(
                top = 20.dp
            ).padding(
                horizontal = 16.dp
            ).background(
                color = Color(0xFFF6F5FE),
                shape = RoundedCornerShape(16.dp)
            ).padding(
                horizontal = 14.dp,
                vertical = 15.dp
            ), horizontalAlignment = Alignment.CenterHorizontally)
            {
                Row(modifier = Modifier.fillMaxWidth()) {

                    Column(modifier = Modifier.weight(1f)) {
                        AppText(text = stringResource(Res.string.paste_video_url),
                            font = Res.font.nunito_semibold,
                            fontSize = 18.sp,
                            color = Color(0xFF1F2937))
                        AppText(text = stringResource(Res.string.we_support_many_platforms),
                            font = Res.font.nunito_medium,
                            fontSize = 14.sp,
                            color = Color(0xFF1F2937).copy(
                                alpha = 0.7f
                            ))
                    }
                    Box(modifier = Modifier.background(
                        color = Color(0xFF3067FC).copy(
                            alpha = 0.14f
                        ),
                        shape = RoundedCornerShape(7.dp)
                    ).clip(
                        shape = RoundedCornerShape(7.dp)
                    ).clickable(indication = null, interactionSource = remember { MutableInteractionSource() },
                        onClick = {
                           //TODO Implement getting clipboard
                        }).padding(
                        8.dp
                    )) {
                        Image(imageVector = vectorResource(
                            Res.drawable.ic_onboarding_card1_img1
                        ),contentDescription = null)
                    }

                }
                BasicTextField(
                    value = link,
                    onValueChange = { link = it },
                    singleLine = true,
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .background(Color.White, RoundedCornerShape(12.dp))
                        .border(width = 1.dp, color = Color(0xFFECECEC),
                            shape = RoundedCornerShape(12.dp)).padding(
                            vertical = 8.dp,
                            horizontal = 4.dp
                        )
                        .onFocusChanged { focusState -> isFocused = focusState.isFocused }
                        .fillMaxWidth(),
                    textStyle = TextStyle(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 16.sp
                    ),
                    decorationBox = { innerTextField ->

                        Box(
                            modifier = Modifier
                                .fillMaxWidth().padding(
                                    start = 15.dp, end = 8.dp
                                )
                                .padding(vertical = 8.dp) // outer padding for icons/text
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {

                                // Leading icon
                                Image(
                                    imageVector = vectorResource(Res.drawable.ic_link),
                                    contentDescription = null,
                                )

                                // Placeholder / Text
                                Box(modifier = Modifier.weight(1f).padding(
                                    horizontal = 5.dp
                                )) {
                                    if (link.isEmpty() && !isFocused) {
                                        AppText(
                                            text = placeHolder,
                                            fontSize = 16.sp,
                                            color = MaterialTheme.colorScheme.onSurface,
                                            textAlign = TextAlign.Start
                                        )
                                    }

                                    innerTextField() // actual text input
                                }
                            }
                        }
                    }
                )
                if (downloaderUiState.isLoading){
                    CircularProgressIndicator(
                        modifier = Modifier.padding(
                            top = 10.dp
                        ).size(40.dp),
                        trackColor = Color.White,
                        color = Color(0xFF2246F7)
                    )
                }else{
                    AppMainButton(
                        modifier = Modifier.fillMaxWidth().clickable(indication = null,
                            interactionSource = remember { MutableInteractionSource() }, onClick = {
                                homeScreenViewModel.onEvent(HomeScreenViewModel.HomeScreenViewModelEvents.DownloadBtnClick(link))
                            }).padding(
                            top = 14.dp
                        ), showDownload = true,
                        text = "Download"
                    )
                }

            }

            Row(modifier = Modifier.fillMaxWidth().padding(
                horizontal = 16.dp
            ).padding(
                top = 15.dp
            ), verticalAlignment = Alignment.CenterVertically) {
                AppText(text = stringResource(Res.string.top_supported_platforms),
                    color = Color(0xFF1F2937),
                    fontSize = 18.sp,
                    font = Res.font.nunito_bold,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Start)

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable(indication = null,
                        interactionSource = remember { MutableInteractionSource() },onClick = {
                            showMore = !showMore
                    })
                ) {
                    AppText(text = if (showMore) stringResource(Res.string.less) else "More",
                        color = Color(0xFF003BEE),
                        font = Res.font.nunito_semibold,
                        fontSize = 14.sp, modifier = Modifier.padding(
                            end = 6.dp
                        ))
                    Image(imageVector = vectorResource(
                        Res.drawable.ic_down_arrow
                    ), contentDescription = null)
                }


            }

            Row(modifier = Modifier.fillMaxWidth().padding(
                horizontal = 16.dp
            ).padding(
                top = 12.dp
            ),
                horizontalArrangement = Arrangement.SpaceBetween) {
                socialsListRow1.forEach {
                    SocialAppItem(it, onItemClick = {

                        when(it.name){
                            "WA"->{
//                                if (downloaderViewModel.isWhatsAppInstalled(context = activity)){
//                                    downloaderViewModel.normalWAOrBusiness = 1
//                                    navController.navigate(NavRoutes.StatusSaverHomeScreen.route)
//                                }else{
//                                    coroutineScope.launch {
//                                        snackBarState.showSnackbar("Please Install WhatsApp First")
//                                    }
//                                }

                            }
                            "WB"->{
//                                if (downloaderViewModel.isWhatsAppBusinessInstalled(context = activity)){
//                                    downloaderViewModel.normalWAOrBusiness = 2
//                                    navController.navigate(NavRoutes.StatusSaverHomeScreen.route)
//                                }else{
//                                    coroutineScope.launch {
//                                        snackBarState.showSnackbar("Please Install WhatsApp Business First")
//                                    }
//                                }
                            }
                            else -> {
                                downloaderViewModel.selectedSocialMedia = it.name
                                navController.navigate(NavRoutes.DownloaderSpecialScreen.route)
                            }
                        }
                    })
                }
            }

            if (showMore){
                Row(modifier = Modifier.fillMaxWidth().padding(
                    horizontal = 16.dp
                ).padding(
                    top = 12.dp
                ),
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    socialsListRow2.forEach {
                        SocialAppItem(it, onItemClick = {
                            downloaderViewModel.selectedSocialMedia = it.name
                            navController.navigate(NavRoutes.DownloaderSpecialScreen.route)
                        })
                    }
                }
            }
            Row(modifier = Modifier.fillMaxWidth().padding(
                horizontal = 16.dp
            ).padding(
                top = 15.dp
            ), verticalAlignment = Alignment.CenterVertically) {
                AppText(text = stringResource(Res.string.short_videos),
                    color = Color(0xFF1F2937),
                    fontSize = 18.sp,
                    font = Res.font.nunito_bold,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Start)

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable(indication = null,
                        interactionSource = remember { MutableInteractionSource() },onClick = {
                            navController.navigate(NavRoutes.ReelsHomeScreen.route)
                        })
                ) {
                    AppText(text = stringResource(Res.string.view_all),
                        color = Color(0xFF003BEE),
                        font = Res.font.nunito_semibold,
                        fontSize = 14.sp, modifier = Modifier.padding(
                            end = 6.dp
                        ))
                    Image(imageVector = vectorResource(
                        Res.drawable.ic_right_arrow_view_all
                    ), contentDescription = null)
                }


            }
            when (val response = reels) {
                is Baseresponse.Error<*> -> {
                   // Log.d(downloaderTAG,"Error ${response.errormessage.toString()}")
                }

                is Baseresponse.IDLE<*> -> {
                  //  Log.d(downloaderTAG,"IDLE")
                }

                is Baseresponse.Loading<*> -> {
                    val list = List(10) {
                        ""
                    }
                    LazyRow(modifier = Modifier.fillMaxWidth().height(
                        220.dp
                    ).padding(
                        top = 10.dp
                    ), horizontalArrangement = Arrangement.spacedBy(
                        8.dp
                    ), contentPadding = PaddingValues(
                        start = 16.dp, end = 16.dp,
                        bottom = 40.dp
                    )) {

                        items(list){

                            ShortVideoItem(it, onItemClick = {

                            })
                        }

                    }
                }

                is Baseresponse.Success<*> -> {
                   // Log.d(downloaderTAG,"Success ${response.data.toString()}")

                    response.data?.let {responseItem->
                        LazyRow(modifier = Modifier.fillMaxWidth().height(
                            220.dp
                        ).padding(
                             top = 10.dp
                        ), horizontalArrangement = Arrangement.spacedBy(
                            8.dp
                        ), contentPadding = PaddingValues(
                            start = 16.dp , end = 16.dp,
                            bottom = 40.dp
                        )) {
                            items(responseItem.take(10), key = { item->
                                item.id
                            }){ item->
                                ShortVideoItem(item.url, onItemClick = {
                                    navController.navigate(
                                        NavRoutes.ReelsHomeScreen.route
                                    )
                                })
                            }
                        }
                    }
                }
            }
        }
    }, containerColor = Color.White)
    LaunchedEffect(Unit) {
        reelsViewModel.onEvent(ReelsViewModel.ReelsViewModelEvents.GetReelsFromApi)
    }


}

@Preview
@Composable
private fun HomeScreenPrev() {

   // DownloaderHomeScreen(rememberNavController())
}
