package org.example.project.presentation.screens.language

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.cyberarsenals.video.downloader.save.videos.presentation.composables.TopBar
import downloaderkmpproductionapp.shared.generated.resources.Res
import downloaderkmpproductionapp.shared.generated.resources.apply
import downloaderkmpproductionapp.shared.generated.resources.language
import org.example.project.navigation.NavRoutes
import org.example.project.presentation.composables.AppMainButton
import org.example.project.presentation.composables.LanguageSelectionItem
import org.example.project.presentation.viewModels.DownloaderViewModel
import org.example.project.presentation.viewModels.LanguageSelectionViewModel
import org.example.project.utils.PreferencesKeys
import org.jetbrains.compose.resources.stringResource

@Composable
fun LanguageSelection(navController: NavController,
                      languageSelectionViewModel: LanguageSelectionViewModel,
                      downloaderViewModel: DownloaderViewModel) {
    val languageList by languageSelectionViewModel.languageState.collectAsStateWithLifecycle()
    Scaffold(topBar = {
        Column(modifier = Modifier.fillMaxWidth()) {
            TopBar(text = stringResource(Res.string.language), showBack = false, onApplyLangClick = {
                    languageSelectionViewModel.selectedLang?.let {
                        downloaderViewModel.saveString(PreferencesKeys.LANGUAGE_SELECTED, it.langcode)
                        downloaderViewModel.saveBoolean(PreferencesKeys.IS_FIRST_TIME, false)
                        navController.navigate(NavRoutes.OnBoardingScreen.route)
                    }
                }, onApplyTickClick = {
                    languageSelectionViewModel.selectedLang?.let {
                        downloaderViewModel.saveString(PreferencesKeys.LANGUAGE_SELECTED, it.langcode)
                        downloaderViewModel.saveBoolean(PreferencesKeys.IS_FIRST_TIME, false)
                        navController.navigate(NavRoutes.OnBoardingScreen.route)
                    }
                }, onPremiumClick = {
                    navController.navigate(NavRoutes.PremiumScreen.route)
                })
        }
    }, containerColor = Color.White, content = {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(it).padding(
                top = 10.dp
            )) {

            LazyColumn(modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(
                    horizontal = 16.dp,
                )
            ) {
                items(languageList){
                    LanguageSelectionItem(language = it, onItemClick = {
                      languageSelectionViewModel.onEvent(LanguageSelectionViewModel.LanguageSelectionScreenEvents.LangItemClicked(
                          language = it
                      ))
                    })
                }

            }

            AppMainButton(modifier = Modifier.fillMaxWidth().padding(
                horizontal = 16.dp,
            ).padding(
                bottom = 30.dp
            ).clickable(indication = null, interactionSource = remember {
                MutableInteractionSource()
            },onClick = {
                languageSelectionViewModel.selectedLang?.let {
                    downloaderViewModel.saveString(PreferencesKeys.LANGUAGE_SELECTED, it.langcode)
                    downloaderViewModel.saveBoolean(PreferencesKeys.IS_FIRST_TIME, false)
                    navController.navigate(NavRoutes.OnBoardingScreen.route)
                }
            }), text = stringResource(Res.string.apply))

        }
    })

}

@Preview
@Composable
private fun LanguageSelectionPrev() {

}