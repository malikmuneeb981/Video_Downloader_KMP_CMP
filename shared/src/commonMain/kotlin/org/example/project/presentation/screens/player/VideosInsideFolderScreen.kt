package org.example.project.presentation.screens.player

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.cyberarsenals.video.downloader.save.videos.presentation.composables.TopBar
import com.translate.speech.to.text.dictionary.instant.voice.translator.presentation.composables.AppText
import downloaderkmpproductionapp.shared.generated.resources.Res
import downloaderkmpproductionapp.shared.generated.resources.ic_right_arrow
import downloaderkmpproductionapp.shared.generated.resources.nunito_semibold
import org.example.project.navigation.NavRoutes
import org.example.project.presentation.composables.VideoThumbnailView
import org.example.project.presentation.viewModels.DownloaderViewModel
import org.jetbrains.compose.resources.vectorResource


@Composable
fun VideosInsideFolderScreen(navController: NavController,
                             downloaderViewModel: DownloaderViewModel) {
    Scaffold(topBar = {
        Column(modifier = Modifier.fillMaxWidth()) {
            TopBar(
                text = downloaderViewModel.selectedFolderWithFiles?.folderName.toString(),
                showBack = true,
                onBackClick = {
                    navController.navigate(NavRoutes.PlayerHomeScreen.route)
                }, onPremiumClick = {
                    navController.navigate(NavRoutes.PremiumScreen.route)
                })
        }
    }, content = {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(it)
            .padding(
                horizontal = 15.dp
            )) {
            downloaderViewModel.selectedFolderWithFiles?.let {
                LazyColumn(modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(vertical = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(
                        10.dp
                    )) {
                    items(it.files){
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Row(modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    downloaderViewModel.videoToPlayFile = null
                                    downloaderViewModel.videoToPlayUrl = null
                                    downloaderViewModel.videoToPlayUri = it.uri
                                    navController.navigate(NavRoutes.NativeVideoPlayerScreen.route)
                                },
                                horizontalArrangement = Arrangement.spacedBy(
                                    15.dp
                                ),
                                verticalAlignment = Alignment.CenterVertically)
                            {
                                Box(modifier = Modifier
                                    .size(
                                        74.dp
                                    )
                                    .clip(
                                        shape = RoundedCornerShape(12.dp)
                                    )) {
                                    VideoThumbnailView(videoSource = it.uri, contentDescription = null,
                                        modifier = Modifier.matchParentSize(),
                                        contentScale = ContentScale.Crop)
                                }
                                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Center) {
                                    AppText(text = it.name, fontSize = 16.sp, font = Res.font.nunito_semibold,
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Start)

                                    Row(modifier = Modifier.fillMaxWidth().padding(
                                        top = 13.dp
                                    )) {
                                        AppText(text = "",
                                            color = Color(0xFF8B96A6), fontSize = 12.sp,
                                            modifier = Modifier.weight(1f),
                                            textAlign = TextAlign.Start)
                                        Image(imageVector = vectorResource(
                                            Res.drawable.ic_right_arrow
                                        ), contentDescription = null)
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.fillMaxWidth().padding(
                                top = 12.dp
                            ).height(2.dp).background(
                                color = Color(0xFFECECEC)
                            ))
                        }
                    }

                }
            }

            
            
        }
    }, containerColor = Color.White)

}

//ThumbnailUtils.createVideoThumbnail(
//it.path,
//MediaStore.Video.Thumbnails.MINI_KIND
//)

@Preview
@Composable
private fun VideosInsideFolderScreenPrev() {
 //   DownloaderMediaPlayerAsadTheme() {
//        VideosInsideFolderScreen(
//            mediaFolder = MediaFolder(
//                folderName = "adajkda", files = mutableListOf(
//                    MediaFile(uri = "dadsda".toUri(), path = "dsfsfsfs", folderName = "adasdadsa",
//                        name = "asdfds", size = 0L, dateModified = 0L, thumbnail = "dad".toUri(),
//                        duration = 0L)
//                )
//            )
//        )
   // }
}