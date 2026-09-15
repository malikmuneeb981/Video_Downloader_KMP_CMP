package org.example.project.presentation.screens.downloader

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.cyberarsenals.video.downloader.save.videos.domain.models.appmodels.GenericDialogData
import com.cyberarsenals.video.downloader.save.videos.presentation.dialogs.HorizontalBtnsGenericDialog
import com.translate.speech.to.text.dictionary.instant.voice.translator.presentation.composables.AppText
import downloaderkmpproductionapp.shared.generated.resources.Res
import downloaderkmpproductionapp.shared.generated.resources.ic_delete_icon_dialog
import downloaderkmpproductionapp.shared.generated.resources.nunito_semibold
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.example.project.navigation.NavRoutes
import org.example.project.presentation.composables.DownloadedFilesItem
import org.example.project.presentation.composables.DownloadingFileItem
import org.example.project.presentation.composables.HomeSecondaryTopBar
import org.example.project.presentation.composables.NoDownloads
import org.example.project.presentation.viewModels.DownloaderViewModel
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun DownloadsScreen(navController: NavController, downloaderViewModel: DownloaderViewModel) {
    val downloadedFiles = downloaderViewModel.downloadedFilesState.collectAsStateWithLifecycle()
    val downloadProgressMap by downloaderViewModel.downloadProgress.collectAsStateWithLifecycle()
    val downloadThumbnailsMap by downloaderViewModel.downloadingThumbnail.collectAsStateWithLifecycle()
    val tabs = listOf("In Progress", "Downloaded")
    val pagerState = rememberPagerState(pageCount = { tabs.size })
    val scope = rememberCoroutineScope()
    val downloadingProgress by downloaderViewModel.downloadingProgress.collectAsStateWithLifecycle()
    // val activity = LocalActivity.current as Activity
    var deleteFileDialog by remember { mutableStateOf(false) }
    var filePathToDelete by remember { mutableStateOf("") }
    if (deleteFileDialog){
        HorizontalBtnsGenericDialog(
            dialogData = GenericDialogData(
                image = Res.drawable.ic_delete_icon_dialog,
                title = "Delete Video",
                subTitle = "Are you sure you want to delete this file from your device?",
                negativeBtnText = "Cancel",
                positiveBtnText = "Yes, Delete",
            ), onDismiss = {
                deleteFileDialog = false
            }, onPositiveBtnClick = {
                deleteFileDialog = false
                downloaderViewModel.deleteFile(
                    path = filePathToDelete,
                )
                downloaderViewModel.getAllVideosInFolder(statuses = false)
            }, onNegativeBtnClick = {
                deleteFileDialog = false
            }
        )
    }
    LaunchedEffect(Unit) {
        downloaderViewModel.getAllVideosInFolder()
        while (true) {
            withContext(Dispatchers.IO) {
                val iterator = downloadProgressMap.entries.iterator()
                while (iterator.hasNext()) {
                    val id = iterator.next()
                    downloaderViewModel.getDownloadProgress( id.key)
                    val progress = downloadingProgress
                    if (progress == 100) {
                     //   Log.d("DownloadProgress", "Download ID $id: Completed/Failed")
                        delay(5000.milliseconds)
                        //iterator.remove()  // Safely remove the item from the list
                        downloaderViewModel.removeDownload(id.key)
                        downloaderViewModel.removeDownloadingVidThumbnail(id.key)
                        downloaderViewModel.getAllVideosInFolder()
                    } else {
                       // Log.d("DownloadProgress", "Download ID $id: $progress%")
                        progress?.let {
                            downloaderViewModel.updateProgress(id.key, it)
                        }

                    }
                }
                delay(1000.milliseconds)  // Update every second
            }
        }
    }
    Scaffold(topBar = {
        Column(modifier = Modifier.fillMaxWidth()) {
            HomeSecondaryTopBar(navController = navController, text = "Downloads",
                showBack = true, onBackClick = {
                   navController.popBackStack()
                })
        }
    }, containerColor = Color.White, content = {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(
                it
            )) {

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

            // 🔥 Pager Content
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->

                when(page){
                    0->{
                        if (downloadProgressMap.entries.isNotEmpty()){
                           // Log.d("downloadProgressMap",downloadProgressMap.entries.toString())
                            LazyColumn(
                                modifier = Modifier.fillMaxSize().padding(
                                    horizontal = 15.dp
                                ),
                                contentPadding = PaddingValues(vertical = 16.dp)
                            ) {
                                items(downloadProgressMap.toList()) { (id, progress) ->
                                    DownloadingFileItem(progress,id, downloadThumbnailsMap[id].toString()){
                                        downloaderViewModel.removeDownloadFromDownloads(it)
                                        downloaderViewModel.removeDownload(it)
                                        downloaderViewModel.removeDownloadingVidThumbnail(it)
                                       // Log.d("downloadProgressMap",downloadProgressMap.entries.toString())
                                    }
                                }
                            }
                        }else{
                            NoDownloads()
                        }
                    }
                    1->{
                        if (downloadedFiles.value.isNotEmpty()){
                            LazyColumn(
                                modifier = Modifier.fillMaxSize().padding(
                                    horizontal = 15.dp
                                ),
                                contentPadding = PaddingValues(vertical = 16.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                items(downloadedFiles.value) {

                                    DownloadedFilesItem(it)
                                    { option,file->
                                        when(option){
                                            "Play"->{
                                                downloaderViewModel.videoToPlayFile = file
                                                downloaderViewModel.videoToPlayUrl = null
                                                downloaderViewModel.videoToPlayUri =null
                                                navController.navigate(NavRoutes.VideoPlayerScreen.route)
                                            }
                                            "Share"->{
//                                                downloaderViewModel.shareFile(
//                                                    context = activity, path = it.path,
//                                                    isVideo = true
//                                                )
                                            }
                                            "Delete"->{
                                                filePathToDelete = it
                                                deleteFileDialog = true
                                            }
                                        }

                                    }

                                }
                            }
                        }
                        else{
                            NoDownloads()
                        }
                    }
                    else -> {

                    }
                }

            }
        }

    })

}

@Preview
@Composable
private fun DownloadsScreenPrev() {
   // DownloaderMediaPlayerAsadTheme() {
        //DownloadsScreen()
    //}
}