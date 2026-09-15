package org.example.project.presentation.screens.trending

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cyberarsenals.video.downloader.save.videos.presentation.composables.TopBar
import downloaderkmpproductionapp.shared.generated.resources.Res
import downloaderkmpproductionapp.shared.generated.resources.trending
import org.example.project.commons.BackHandler
import org.example.project.commons.WebView
import org.example.project.navigation.NavRoutes
import org.example.project.presentation.viewModels.DownloaderViewModel
import org.jetbrains.compose.resources.stringResource

@Composable
fun WebViewScreen(navController: NavController,downloaderViewModel: DownloaderViewModel) {

    val url = downloaderViewModel.urlToLoad
    BackHandler {
        navController.popBackStack()
    }
    Scaffold(topBar = {
        Column(modifier = Modifier.fillMaxWidth()) {
            TopBar(text = stringResource(Res.string.trending), showBack = true, onPremiumClick = {
                navController.navigate(NavRoutes.PremiumScreen.route)
            }, onBackClick = {
                navController.popBackStack()
            })
        }
    }, content = { paddingValues ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(
                top = paddingValues.calculateTopPadding(),
                bottom = (paddingValues.calculateBottomPadding() - 65.dp).coerceAtLeast(0.dp)
            )) {
            url?.let {
                WebView(url = it, modifier = Modifier.fillMaxSize())
            }

        }
    }, containerColor = Color.White)

}