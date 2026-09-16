package org.example.project.presentation.screens.downloader

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.cyberarsenals.video.downloader.save.videos.domain.models.appmodels.WsStatusModel
import com.cyberarsenals.video.downloader.save.videos.presentation.composables.TopBar
import com.translate.speech.to.text.dictionary.instant.voice.translator.presentation.composables.AppText
import downloaderkmpproductionapp.shared.generated.resources.Res
import downloaderkmpproductionapp.shared.generated.resources.app_name
import downloaderkmpproductionapp.shared.generated.resources.grant_access
import downloaderkmpproductionapp.shared.generated.resources.ic_nodownloads
import downloaderkmpproductionapp.shared.generated.resources.nunito_semibold
import downloaderkmpproductionapp.shared.generated.resources.photos
import downloaderkmpproductionapp.shared.generated.resources.status_downloaded
import downloaderkmpproductionapp.shared.generated.resources.status_saver
import downloaderkmpproductionapp.shared.generated.resources.status_saver_tab_saved
import downloaderkmpproductionapp.shared.generated.resources.videos
import downloaderkmpproductionapp.shared.generated.resources.we_need_permission
import kotlinx.coroutines.launch
import org.example.project.commons.RecentStatuses
import org.example.project.navigation.NavRoutes
import org.example.project.presentation.composables.AppMainButton
import org.example.project.presentation.composables.StatusSaverItem
import org.example.project.presentation.viewModels.DownloaderViewModel
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun StatusSaverHomeScreen(navController: NavController,
                          downloaderViewModel: DownloaderViewModel) {
    val tabs = listOf(stringResource(Res.string.photos),stringResource(Res.string.videos), stringResource(
        Res.string.status_saver_tab_saved))
    val pagerState = rememberPagerState(pageCount = { tabs.size })
    val scope = rememberCoroutineScope()
  //  val activity = LocalActivity.current as Activity
    var statusListPhotos by remember { mutableStateOf<List<WsStatusModel>>(emptyList()) }
    var statusListVideos by remember { mutableStateOf<List<WsStatusModel>>(emptyList()) }
    val downloadedStatuses by downloaderViewModel.downloadedFilesState.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val name = stringResource(Res.string.app_name)
    val appName by remember { mutableStateOf(name) }
    LaunchedEffect(Unit) {
        downloaderViewModel.getAllVideosInFolder(statuses = true)
    }
    Scaffold(topBar = {
        Column(modifier = Modifier.fillMaxWidth()) {
            TopBar(text = stringResource(Res.string.status_saver), showBack = true, onBackClick = {
                navController.navigate(NavRoutes.DownloaderHomeScreen.route)
            }, onPremiumClick = {
                navController.navigate(NavRoutes.PremiumScreen.route)
            })
        }
    }, snackbarHost = {
        SnackbarHost(snackBarHostState)
    },content = {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {

            TabRow(
                selectedTabIndex = pagerState.currentPage,
                // edgePadding = 16.dp,
                containerColor = Color.White,
                divider = {
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = Color.LightGray.copy(alpha = 0.5f)
                    )
                }, // remove bottom line
                indicator = { tabPositions ->

                    // 🔥 Custom Indicator
                    TabRowDefaults.PrimaryIndicator(
                        modifier = Modifier
                            .tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                        height = 4.dp,
                        width = 120.dp,
                        color = Color(0xFF1E4DFF),
                        shape = RoundedCornerShape(topStart = 10.dp,
                            topEnd = 10.dp)

                    )
                }, modifier = Modifier.fillMaxWidth()
            ) {

                tabs.forEachIndexed { index, title ->

                    val isSelected = pagerState.currentPage == index

                    Tab(
                        selected = isSelected,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }
                    ) {

                        // 🔥 Fully Custom Tab UI
                        Box(
                            modifier = Modifier
                                .padding(vertical = 5.dp, horizontal = 12.dp)
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            AppText(
                                text = title,
                                color = if (isSelected)
                                    Color(0xFF1E4DFF)
                                else
                                    Color(0xFF1E1E1E),
                                font = Res.font.nunito_semibold,
                                fontSize = 16.sp

                            )
                        }
                    }
                }
            }
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->

                when(page){
                    0->{
                        if (statusListPhotos.isNotEmpty()){
                            StatusLazyColumn(statusList = statusListPhotos, onSaveClick = {
                                println("item Clicked + ${it.toString()}")
                                    downloaderViewModel.downloadStatus(isVideo = it.fileUri?.endsWith(".mp4") == true, fileUri = it.fileUri.toString(),
                                        fileName = it.fileName.toString(), appName = appName)
//                                    coroutineScope.launch {
//                                        snackBarHostState.showSnackbar(message = activity.getString(Res.string.status_downloaded))
//                                    }
                                }, onShareClick = {
//                                    downloaderViewModel.shareFile(context = activity, path = it.fileUri.toString(),
//                                        isVideo = it.fileUri?.endsWith(".mp4") == true
//                                    )
                                }, onItemClick = {
                                   // Log.d("File Clicked",it.fileUri.toString())
                                if (it.fileUri?.endsWith(".mp4")==true){
                                    downloaderViewModel.videoToPlayUri = it.fileUri
                                    navController.navigate(NavRoutes.VideoPlayerScreen.route)
                                }else{
                                    downloaderViewModel.statusSaverSelectedFile = it.fileUri
                                    navController.navigate(NavRoutes.ImageViewScreen.route)
                                }
                                })
                        }else{
                            RecentStatuses(photoOrVideo = 1,downloaderViewModel = downloaderViewModel,
                                 onResult = {
                                    statusListPhotos = it
                                   // Log.d("WA STATUS Result",it.toString())
                                })
                        }
                    }
                    1->{
                        if (statusListVideos.isNotEmpty()){
                            StatusLazyColumn(statusList = statusListVideos, onSaveClick = {
                                downloaderViewModel.downloadStatus(isVideo = it.fileUri?.endsWith(".mp4") == true, fileUri = it.fileUri.toString(),
                                    fileName = it.fileName.toString(), appName = appName)
//                                coroutineScope.launch {
//                                    snackBarHostState.showSnackbar(message = activity.getString(Res.string.status_downloaded))
//                                }
                            }, onShareClick = {
//                                downloaderViewModel.shareFile(context = activity, path = it.fileUri.toString(),
//                                    isVideo = it.fileUri?.endsWith(".mp4") == true
//                                )
                            }, onItemClick = {
                               // Log.d("File Clicked",it.fileUri.toString())
                                if (it.fileUri?.endsWith(".mp4")==true){
                                    downloaderViewModel.videoToPlayUri =it.fileUri
                                    navController.navigate(NavRoutes.VideoPlayerScreen.route)
                                }else{
                                    downloaderViewModel.statusSaverSelectedFile = it.fileUri
                                    navController.navigate(NavRoutes.ImageViewScreen.route)
                                }
                            })
                        }else{
                            RecentStatuses(photoOrVideo = 2,downloaderViewModel = downloaderViewModel,
                                 onResult = {
                                    statusListVideos = it
                                   // Log.d("WA STATUS Result",it.toString())
                                })
                        }
                    }
                    2->{
                        downloaderViewModel.getAllVideosInFolder(statuses = true)
                        LazyVerticalGrid(modifier = Modifier.fillMaxSize(),
                            columns = GridCells.Fixed(2),
                            horizontalArrangement = Arrangement.spacedBy(20.dp),
                            contentPadding = PaddingValues(vertical = 10.dp, horizontal = 15.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)) {

                            val mappedList = downloadedStatuses.map {
                                WsStatusModel(fileName = it,
                                    fileUri = it)
                            }
                            println("Downloaded Statuses ::: $mappedList.toString()")
                            items(mappedList){
                                StatusSaverItem(statusModel = it, saved = true, onSaveClick = {}, onDeleteClick = {
                                   // Log.d("Delete Clicked",it.fileUri.toString())
                                    downloaderViewModel.deleteFile( path = it.fileUri.toString())
                                    downloaderViewModel.getAllVideosInFolder(statuses = true)
//                                    coroutineScope.launch {
//                                        snackBarHostState.showSnackbar(message = activity.getString(Res.string.file_deleted))
//                                    }
                                }, onShareClick = {
//                                    downloaderViewModel.shareFile(context = activity, path = it.fileUri.toString(),
//                                        isVideo = it.fileUri?.endsWith(".mp4") == true
//                                    )
                                }, onItemClick = {
                                    if (it.fileUri?.endsWith(".mp4")==true){
                                        downloaderViewModel.videoToPlayUri =it.fileUri
                                        navController.navigate(NavRoutes.VideoPlayerScreen.route)
                                    }else{
                                        downloaderViewModel.statusSaverSelectedFile = it.fileUri
                                        navController.navigate(NavRoutes.ImageViewScreen.route)
                                    }
                                })
                            }
                        }
                    }
                    else -> {

                    }
                }

            }

        }
    }, containerColor = Color.White)
}

@Composable
fun StatusLazyColumn(statusList: List<WsStatusModel>,onSaveClick:(WsStatusModel)-> Unit,
                     onShareClick:(WsStatusModel)-> Unit,onItemClick:(WsStatusModel)-> Unit) {
    LazyVerticalGrid(modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        contentPadding = PaddingValues(vertical = 10.dp, horizontal = 15.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)) {

        items(statusList){
            StatusSaverItem(statusModel = it, saved = false, onSaveClick = { statusModel->
                onSaveClick(statusModel)
            }, onDeleteClick = {}, onShareClick = {
                onShareClick(it)
            }, onItemClick = {
                onItemClick(it)
            })
        }
    }
}


@Preview
@Composable
private fun StatusSaverHomeScreenPrev() {
   // DownloaderMediaPlayerAsadTheme() {
      //  RecentStatuses()
   // }


}