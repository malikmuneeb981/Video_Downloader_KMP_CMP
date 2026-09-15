package com.cyberarsenals.video.downloader.save.videos.presentation.screens.more

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.cyberarsenals.video.downloader.save.videos.presentation.composables.TopBar
import com.translate.speech.to.text.dictionary.instant.voice.translatoRes.presentation.composables.BottomNav
import com.translate.speech.to.text.dictionary.instant.voice.translator.presentation.composables.AppText
import downloaderkmpproductionapp.shared.generated.resources.Res
import downloaderkmpproductionapp.shared.generated.resources.buy_now
import downloaderkmpproductionapp.shared.generated.resources.disabled
import downloaderkmpproductionapp.shared.generated.resources.download_without_leaving
import downloaderkmpproductionapp.shared.generated.resources.enabled
import downloaderkmpproductionapp.shared.generated.resources.english
import downloaderkmpproductionapp.shared.generated.resources.favorite_reels
import downloaderkmpproductionapp.shared.generated.resources.ic_downloads_more
import downloaderkmpproductionapp.shared.generated.resources.ic_fav_reels_more
import downloaderkmpproductionapp.shared.generated.resources.ic_moretools_language
import downloaderkmpproductionapp.shared.generated.resources.ic_moretools_privacypolicy
import downloaderkmpproductionapp.shared.generated.resources.ic_moretools_rateus
import downloaderkmpproductionapp.shared.generated.resources.ic_moretools_shareapp
import downloaderkmpproductionapp.shared.generated.resources.ic_premium_more
import downloaderkmpproductionapp.shared.generated.resources.ic_right_arrow
import downloaderkmpproductionapp.shared.generated.resources.language
import downloaderkmpproductionapp.shared.generated.resources.more_tools
import downloaderkmpproductionapp.shared.generated.resources.nunito_bold
import downloaderkmpproductionapp.shared.generated.resources.premium_plan
import downloaderkmpproductionapp.shared.generated.resources.privacy_policy
import downloaderkmpproductionapp.shared.generated.resources.rate_us
import downloaderkmpproductionapp.shared.generated.resources.share_with_friends
import downloaderkmpproductionapp.shared.generated.resources.unlock_unlimited_dowloads
import org.example.project.commons.BackHandler
import org.example.project.navigation.NavRoutes
import org.example.project.presentation.composables.DrawerRow
import org.example.project.presentation.viewModels.DownloaderViewModel
import org.example.project.presentation.viewModels.LanguageSelectionViewModel
import org.example.project.utils.PreferencesKeys
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun MoreScreen(navController: NavController,
               downloaderViewModel: DownloaderViewModel,
               languageSelectionViewModel: LanguageSelectionViewModel
) {

   // val isDarkTheme by downloaderViewModel.isDarkTheme.collectAsStateWithLifecycle()
    val currentSelectedLangCode by downloaderViewModel.languageSelectedState.collectAsStateWithLifecycle()
    var lang: String? by remember { mutableStateOf("") }
    val outSideAppDownloadSwitch = downloaderViewModel.outSideAppDownload.collectAsStateWithLifecycle()
  //  var appthemeBtmSheet by remember { mutableStateOf(false) }
//    val appName = stringResource(R.string.app_name)

    BackHandler {
        navController.navigate(NavRoutes.DownloaderHomeScreen.route)
    }
    LaunchedEffect(currentSelectedLangCode) {
        lang = languageSelectionViewModel.allLanguages.find {
            it.langcode == currentSelectedLangCode
        }?.name
    }
    LaunchedEffect(Unit) {
        downloaderViewModel.getCurrentLanguage()
       // downloaderViewModel.getAppTheme()
        downloaderViewModel.checkOutSideDownloadAllowed()
    }
    Scaffold(topBar = {
        Column(modifier = Modifier.fillMaxWidth()) {
            TopBar(text = stringResource(Res.string.more_tools), showBack = false, onPremiumClick = {
                navController.navigate(NavRoutes.PremiumScreen.route)
            })
        }
    }, content = { paddingValues ->

//        if (appthemeBtmSheet){
//            AppThemeDialog(
//                dismiss = { appthemeBtmSheet = false },
//                lightThemeEnabled = { if (it) downloaderViewModel.setDarkTheme(false) },
//                darkThemeEnabled = { if (it) downloaderViewModel.setDarkTheme(true) },
//                systemDefault = { if (it) downloaderViewModel.setDarkTheme(null) },
//                currentlySelected = isDarkTheme
//            )
//        }
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(
                top = paddingValues.calculateTopPadding(),
                bottom = (paddingValues.calculateBottomPadding() - 39.dp).coerceAtLeast(0.dp)
            )
            .padding(
                horizontal = 16.dp,
                vertical = 10.dp
            ).verticalScroll(rememberScrollState())) {


            Column(modifier = Modifier.fillMaxWidth().padding(
                top = 10.dp
            ).clip(shape = RoundedCornerShape(32.dp)).clickable(indication = null,
                interactionSource = remember { MutableInteractionSource() },onClick = {
               navController.navigate(NavRoutes.PremiumScreen.route)
            }).background(
                brush = Brush.verticalGradient(listOf(
                    Color(0xFF2761FB),Color(0xFF2246F7),Color(0xFF0C3AED),
                )),
                shape = RoundedCornerShape(32.dp)
            ).border(
                width = 2.dp,
                shape = RoundedCornerShape(32.dp),
                color = Color.White.copy(
                    alpha = 0.47f
                )
            ).padding(
                vertical = 12.dp, horizontal = 16.dp
            ), verticalArrangement = Arrangement.spacedBy(
                10.dp
            )) {

                Row(modifier = Modifier.fillMaxWidth()) {
                    Image(painter = painterResource(Res.drawable.ic_premium_more),
                        contentDescription = null,
                        modifier = Modifier.size(60.dp))
                    Column(modifier = Modifier.weight(1f).padding(
                        start = 12.dp
                    )) {
                        AppText(text = stringResource(Res.string.premium_plan),
                            font = Res.font.nunito_bold,
                            fontSize = 18.sp,
                            color = Color.White)
                        AppText(text = stringResource(Res.string.unlock_unlimited_dowloads),
                            fontSize = 12.sp,
                            color = Color.White,
                            textAlign = TextAlign.Start,
                            modifier = Modifier.padding(
                                top = 4.dp
                            ))
                    }
                }
                Row(modifier = Modifier.fillMaxWidth().padding(
                    top = 10.dp
                ).padding(
                    horizontal = 8.dp
                ).clip(RoundedCornerShape(30.dp)).background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color(0xFF5881EE),Color(0xFF536FF6),Color(0xFF506CD8)),
                    ), shape = RoundedCornerShape(30.dp)
                ).border(width = 2.dp,
                    color = Color.White.copy(
                        alpha = 0.47f
                    ), shape =
                        RoundedCornerShape(30.dp)
                ).padding(
                    vertical = 14.dp
                ), horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically) {
                    AppText(text = stringResource(Res.string.buy_now),
                        font = Res.font.nunito_bold,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White)


                }


            }
            Column(modifier = Modifier.fillMaxWidth().padding(
                top = 10.dp
            ).background(
                color = Color(0xFFF9FAFB),
                shape = RoundedCornerShape(12.dp)
            ).padding(
                15.dp
            ), verticalArrangement = Arrangement.spacedBy(
                12.dp
            )) {

                AppText(text = stringResource(Res.string.download_without_leaving), fontSize = 12.sp)
                Row(modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    AppText(text = if (
                        true
                        //outSideAppDownloadSwitch.value
                        ) stringResource(Res.string.enabled) else stringResource(Res.string.disabled),
                        fontSize = 16.sp,)

                    Switch(checked =
                        outSideAppDownloadSwitch.value
                        ,
                        onCheckedChange = {
                            downloaderViewModel.saveBoolean(PreferencesKeys.OUTSIDE_APP_DOWNLOADER,it)
                            downloaderViewModel.checkOutSideDownloadAllowed()
                        }, modifier = Modifier.scale(0.5f),
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = Color(0xFF003BEE)
                        ))
                }

            }
            Column(modifier = Modifier.fillMaxWidth().padding(
                top = 10.dp
            ).background(
                color = Color(0xFFF9FAFB),
                shape = RoundedCornerShape(12.dp)
            ).padding(
                horizontal = 16.dp,
                vertical = 20.dp
            ), verticalArrangement = Arrangement.spacedBy(
                12.dp
            )) {
                Row(modifier = Modifier.fillMaxWidth().clickable {
                    navController.navigate(NavRoutes.LanguageScreenInApp.route)
                },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween) {

                    Image(imageVector = vectorResource( Res.drawable.ic_moretools_language),
                        contentDescription = null, modifier = Modifier)

                    AppText(text = stringResource(Res.string.language),
                        textAlign = TextAlign.Left, modifier = Modifier.weight(1f).padding(start = 15.dp), textLines = 1,
                        autoResize = true,
                        fontSize = 16.sp)

                    AppText(text = lang ?: stringResource(Res.string.english), fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Left, textLines = 1,
                        autoResize = true, color = Color(0xFF003BEE),
                        fontSize = 12.sp, modifier = Modifier.padding(
                            end = 5.dp
                        ))
                    Image(imageVector = vectorResource(
                        Res.drawable.ic_right_arrow
                    ), contentDescription = null, colorFilter = ColorFilter.tint(
                        color = Color(0xFF003BEE)
                    ))

                }
                Spacer(modifier = Modifier.fillMaxWidth().background(
                    color = Color(0xFFECECEC)
                ).height(1.dp))
                DrawerRow(image = Res.drawable.ic_downloads_more,
                    text = "Downloads") {
                    navController.navigate(NavRoutes.DownloadsScreen.route)
                }
                Spacer(modifier = Modifier.fillMaxWidth().background(
                    color = Color(0xFFECECEC)
                ).height(1.dp))
                DrawerRow(image = Res.drawable.ic_fav_reels_more,
                    text = stringResource(Res.string.favorite_reels)) {
                    navController.navigate(NavRoutes.FavReelsScreen.route)
                }

            }
            Column(modifier = Modifier.fillMaxWidth().padding(
                top = 10.dp, bottom = 40.dp
            ).background(
                color = Color(0xFFF9FAFB),
                shape = RoundedCornerShape(12.dp)
            ).padding(
                horizontal = 16.dp,
                vertical = 20.dp
            ), verticalArrangement = Arrangement.spacedBy(
                12.dp
            )) {
                DrawerRow(image = Res.drawable.ic_moretools_shareapp,
                    text = stringResource(Res.string.share_with_friends)) {
                    downloaderViewModel.shareText(
                        text = "Check out this amazing app https://play.google.com/store/apps/details?id=com.cyberarsenals.video.downloader.save.videos&hl=en"
                    )
                }
                Spacer(modifier = Modifier.fillMaxWidth().background(
                    color = Color(0xFFECECEC)
                ).height(1.dp))
                DrawerRow(image = Res.drawable.ic_moretools_rateus,
                    text = stringResource(Res.string.rate_us)) {

                }
                Spacer(modifier = Modifier.fillMaxWidth().background(
                    color = Color(0xFFECECEC)
                ).height(1.dp))
                DrawerRow(image = Res.drawable.ic_moretools_privacypolicy,
                    text = stringResource(Res.string.privacy_policy)) {
                    downloaderViewModel.openLink(url = downloaderViewModel.Privacy_Policy)
                }

            }



        }
    }, bottomBar = {
        BottomNav(navController = navController)
    }, containerColor = Color.White)
}

@Preview
@Composable
private fun MoreScreenPrev() {

       // MoreScreen(rememberNavController())


}