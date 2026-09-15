package org.example.project.presentation.screens.reels

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import org.example.project.commons.BackHandler
import org.example.project.commons.DownloadFileManager
import org.example.project.presentation.viewModels.DownloaderViewModel
import org.example.project.presentation.viewModels.ReelsViewModel
import org.example.project.utils.Baseresponse
import org.koin.compose.getKoin

@Composable
fun ReelsHomeScreen(
    navController: NavController,
    downloaderViewModel: DownloaderViewModel,
    reelsViewModel: ReelsViewModel,
    downloadFileManager: DownloadFileManager = getKoin().get()
) {

    val reels by reelsViewModel.reelsResponse.collectAsStateWithLifecycle()
   // val likedReels by reelsViewModel.likedReels.collectAsStateWithLifecycle()
    //val bookmarkedReels by reelsViewModel.bookmarkedReels.collectAsStateWithLifecycle()
    val Reels_TAG = "Reels Screen"
   // val activity = LocalActivity.current as Activity
    val coroutineScope = rememberCoroutineScope()
    val snackBarHost = remember { SnackbarHostState() }
    val isMuted by downloaderViewModel.reelsMute.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        reelsViewModel.onEvent(ReelsViewModel.ReelsViewModelEvents.GetReelsFromApi)
        downloaderViewModel.checkReelsMute()
    }
    BackHandler {
        navController.popBackStack()
    }

    Column(modifier = Modifier
        .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        when (val response = reels) {
            is Baseresponse.Error<*> -> {
                //Log.d(Reels_TAG,"Error ${response.errormessage.toString()}")
            }

            is Baseresponse.IDLE<*> -> {
                ////Log.d(Reels_TAG,"IDLE")
            }

            is Baseresponse.Loading<*> -> {
                CircularProgressIndicator(
                    trackColor = Color.White,
                    color = Color.White
                )
            }

            is Baseresponse.Success<*> -> {
               // Log.d(Reels_TAG,"Success ${response.data.toString()}")

                response.data?.let { reelsList ->
                    val pagerState = rememberPagerState(pageCount = { reelsList.size })
                    VerticalPager(
                        state = pagerState,
                        modifier = Modifier.fillMaxSize(),
                    ) { page ->
                        val isVisible = pagerState.currentPage == page
                        val reel = reelsList[page]

                        VideoPlayerItem(
                            reel = reel,
                            isPlaying = isVisible,
                            isLiked = reel.isLiked,
                            isBookMarked = reel.isBookmarked,
                            isMuted = isMuted,
                            onMutedClick = { muted ->
                                downloaderViewModel.setReelsMute(muted)
                            },
                            onLikeClick = { item, liked ->
                                item.isLiked = !liked
                            },
                            onShareClick = { item ->
                                // Handle share
                            },
                            onBookMarkClick = { item, bookmarked ->
                                item.isBookmarked = !bookmarked
                            },
                            downloadClick = { item ->
                                // Handle download
                            },
                            onBackPressed = {
                                navController.popBackStack()
                            }
                        )
                    }

                }


            }
        }
    }

}