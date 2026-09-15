package org.example.project.presentation.screens.trending

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cyberarsenals.video.downloader.save.videos.presentation.composables.TopBar
import org.example.project.commons.BackHandler
import org.example.project.navigation.NavRoutes
import org.example.project.presentation.viewModels.DownloaderViewModel

@Composable
fun SearchVideosScreen(navController: NavController,downloaderViewModel: DownloaderViewModel) {

    val randomHeight = listOf(164.dp, 226.dp).random()
    val randomWidth = listOf(226.dp, 164.dp).random()

    BackHandler {
        navController.navigate(NavRoutes.TrendingHomeScreen.route)
    }
    Scaffold(topBar = {
        Column(modifier = Modifier.fillMaxWidth()) {
            TopBar(text = downloaderViewModel.searchedVideosResponse?.keyword.toString(), showBack = true, onBackClick = {
                navController.navigate(NavRoutes.TrendingHomeScreen.route)
            }, onPremiumClick = {
                navController.navigate(NavRoutes.PremiumScreen.route)
            })
        }
    }, content = { paddingValues ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(
                paddingValues
            )) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize().padding(
                    horizontal = 16.dp
                ), verticalArrangement = Arrangement.spacedBy(
                    8.dp
                ), horizontalArrangement = Arrangement.spacedBy(15.dp),
                contentPadding = PaddingValues(vertical = 15.dp)
            ) {
                items(items = downloaderViewModel.searchedVideosResponse?.videos?:emptyList(), key = {
                    it.id.toString()
                }){
                    TrendingVideoItem(
                        video = it,
                        modifier = Modifier.size(height = randomHeight,
                            width = randomWidth),
                        videoItemClick = {
                            downloaderViewModel.urlToLoad = it.videoUrl
                            navController.navigate(NavRoutes.WebViewScreen.route)
                        }
                    )
                }

            }

        }
    }, containerColor = Color.White)

}