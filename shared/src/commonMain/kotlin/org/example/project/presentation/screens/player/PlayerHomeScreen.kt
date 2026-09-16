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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.cyberarsenals.video.downloader.save.videos.domain.models.appmodels.PermissionBtmSheetModel
import com.translate.speech.to.text.dictionary.instant.voice.translatoRes.presentation.composables.BottomNav
import com.translate.speech.to.text.dictionary.instant.voice.translator.presentation.composables.AppText
import downloaderkmpproductionapp.shared.generated.resources.Res
import downloaderkmpproductionapp.shared.generated.resources.allow_access
import downloaderkmpproductionapp.shared.generated.resources.home_tab_folders
import downloaderkmpproductionapp.shared.generated.resources.home_tab_videos
import downloaderkmpproductionapp.shared.generated.resources.ic_folder
import downloaderkmpproductionapp.shared.generated.resources.ic_nodownloads
import downloaderkmpproductionapp.shared.generated.resources.ic_right_arrow
import downloaderkmpproductionapp.shared.generated.resources.ic_videos_permission
import downloaderkmpproductionapp.shared.generated.resources.internal_upper
import downloaderkmpproductionapp.shared.generated.resources.no_files_need_permission
import downloaderkmpproductionapp.shared.generated.resources.nunito_bold
import downloaderkmpproductionapp.shared.generated.resources.nunito_semibold
import kotlinx.coroutines.launch
import org.example.project.commons.BackHandler
import org.example.project.commons.MediaPermission
import org.example.project.commons.rememberMediaPermissionState
import org.example.project.domain.models.appmodels.MediaFile
import org.example.project.domain.models.appmodels.MediaFolder
import org.example.project.navigation.NavRoutes
import org.example.project.presentation.btmSheets.PermissionBtmSheet
import org.example.project.presentation.composables.HomeSecondaryTopBar
import org.example.project.presentation.composables.MediaThumbnailImage
import org.example.project.presentation.composables.VideoThumbnailView
import org.example.project.presentation.viewModels.DownloaderViewModel
import org.example.project.presentation.viewModels.MediaReaderViewModel
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun PlayerHomeScreen(
    navController: NavController,
    downloaderViewModel: DownloaderViewModel,
    mediaReaderViewModel: MediaReaderViewModel
) {
    var permissionBtmSheet by remember { mutableStateOf(false) }
    val snackBarState = remember { SnackbarHostState() }
    val permissionState = rememberMediaPermissionState(
        permissions = listOf(MediaPermission.Images, MediaPermission.Videos),
        onPermissionResult = { isGranted ->
            if (isGranted) {
                permissionBtmSheet = false
                mediaReaderViewModel.loadMedia()
                mediaReaderViewModel.loadVideos()
            }
        }
    )
    val tabs = listOf(stringResource(Res.string.home_tab_folders), stringResource(Res.string.home_tab_videos))
    val pagerState = rememberPagerState(pageCount = { tabs.size })
    val scope = rememberCoroutineScope()
    val allVideoFolders by mediaReaderViewModel.mediaFolders.collectAsStateWithLifecycle()
    val allVideoFiles by mediaReaderViewModel.videoList.collectAsStateWithLifecycle()
    val foldersList = allVideoFolders.values.toList()


    BackHandler {
        navController.navigate(NavRoutes.DownloaderHomeScreen.route)
    }

    LaunchedEffect(Unit) {
        if (permissionState.isGranted) {
            mediaReaderViewModel.loadMedia()
            mediaReaderViewModel.loadVideos()
        } else {
            permissionBtmSheet = true
        }
    }

    if (permissionBtmSheet) {
        PermissionBtmSheet(
            permissionBtmSheetModel = PermissionBtmSheetModel(
                image = Res.drawable.ic_videos_permission,
                permissionTitle = "Permission Needed",
                permissionText = "Permission Needed To Access Videos On Device"
            ),
            onDismiss = {
                permissionBtmSheet = false
            },
            onCancelClick = {
                permissionBtmSheet = false
            },
            onGrantClick = {
                permissionState.launchPermissionRequest()
            }
        )
    }
    Scaffold(topBar = {
        HomeSecondaryTopBar(navController = navController,stringResource(Res.string.internal_upper))
    }, snackbarHost = {
        SnackbarHost(snackBarState)
    }, containerColor = Color.White,content =
        { paddingValues ->
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = (paddingValues.calculateBottomPadding() - 39.dp).coerceAtLeast(0.dp)
                ).padding(
                    horizontal = 16.dp
                ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {

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
                    }, modifier = Modifier.fillMaxWidth().padding(
                        top = 10.dp
                    )
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
                            when {
                                permissionState.isGranted -> {
                                    FoldersLazyColumn(foldersList = foldersList, onItemClick = {
                                        downloaderViewModel.selectedFolderWithFiles = it
                                        navController.navigate(NavRoutes.VideosInsideFolderScreen.route)
                                    })
                                }
                                permissionState.shouldShowRationale -> {
                                    // Show rationale UI
                                }
                                else -> {
                                    NoFilesGrantPermission(onGrantClick = {
                                        permissionState.launchPermissionRequest()
                                    })
                                }
                            }

                        }
                        1->{
                            when {
                                permissionState.isGranted -> {
                                    VideosLazyColumn(list = allVideoFiles, onItemClick = {
                                        downloaderViewModel.videoToPlayFile = null
                                        downloaderViewModel.videoToPlayUrl = null
                                        downloaderViewModel.videoToPlayUri = it.uri
                                        navController.navigate(NavRoutes.NativeVideoPlayerScreen.route)
                                    })
                                }
                                permissionState.shouldShowRationale -> {
                                    // Show rationale UI
                                }
                                else -> {
                                    NoFilesGrantPermission(onGrantClick = {
                                        permissionState.launchPermissionRequest()
                                    })
                                }
                            }
                        }
                        else -> {

                        }
                    }

                }


            }
        }, bottomBar = {
        BottomNav(navController = navController)
    })

}

@Composable
fun FoldersLazyColumn(foldersList: List<MediaFolder>,
                      onItemClick:(MediaFolder)-> Unit) {
    LazyColumn(modifier = Modifier
        .fillMaxSize(),
        contentPadding = PaddingValues(top = 25.dp, bottom = 40.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)) {

        items(foldersList,
            key = {
                it.folderName
            }){
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onItemClick(it)
                    },
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically)
                {

                    Box(modifier = Modifier.background(
                        color = Color(0xFF95C0CF).copy(alpha = 0.1f),
                        shape = RoundedCornerShape(12.dp)
                    ).padding(
                        horizontal = 20.dp,
                        vertical = 22.dp
                    )) {
                        Image(imageVector = vectorResource(
                            Res.drawable.ic_folder
                        ), contentDescription = null)
                    }

                    Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start) {
                        AppText(text = it.folderName, fontSize = 16.sp, font = Res.font.nunito_semibold,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Start)

                        Row(modifier = Modifier.fillMaxWidth().padding(
                            top = 13.dp
                        )) {
                            AppText(text = "${it.files.size} Videos",
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

@Composable
fun NoFilesGrantPermission(onGrantClick:()-> Unit) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(
            horizontal = 15.dp
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {

        Image(imageVector = vectorResource(Res.drawable.ic_nodownloads),
            contentDescription = null, modifier = Modifier.size(100.dp))

        AppText(text = stringResource(Res.string.no_files_need_permission), modifier = Modifier.padding(top = 2.dp),
            fontWeight = FontWeight.SemiBold)


        Row(modifier = Modifier
            .padding(
                top = 35.dp,
            )
            .background(
                shape = RoundedCornerShape(30.dp),
                brush = Brush.horizontalGradient(
                    colors = listOf(Color(0xFF2761FB),Color(0xFF2246F7),Color(0xFF0C3AED)),
                )
            )
            .padding(
                horizontal = 10.dp,
                vertical = 5.dp
            )
            .clip(
                shape = RoundedCornerShape(
                    30.dp
                )
            )
            .clickable {

                onGrantClick()
            }
            .padding(
                vertical = 10.dp
            ),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically) {



            AppText(
                text = stringResource(Res.string.allow_access),
                color = Color.White,
                font = Res.font.nunito_bold,
                fontSize = 17.sp,
                modifier = Modifier.padding(
                    start = 8.dp
                )
            )

        }





    }
}

@Composable
fun VideosLazyColumn(list: List<MediaFile>,
                     onItemClick:(MediaFile)-> Unit) {
    LazyColumn(modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(top = 25.dp, bottom = 40.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)) {
        items(list, key = {
            it.uri
        }){
            Column(modifier = Modifier.fillMaxWidth())
            {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onItemClick(it)

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
                        MediaThumbnailImage(model = it.uri ,
                            contentDescription = null,
                            modifier = Modifier.matchParentSize(),
                            contentScale = ContentScale.Crop)

//                        Box(modifier = Modifier
//                            .padding(
//                                bottom = 8.dp, end = 8.dp
//                            )
//                            .background(
//                                color = Color.Black,
//                                shape = RoundedCornerShape(4.dp)
//                            )
//                            .padding(
//                                horizontal = 4.dp
//                            )) {
//                            AppText(text = it.formattedDuration,
//                                color = Color.White,
//                                fontSize = 13.sp,
//                                fontWeight = FontWeight.SemiBold)
//                        }
                    }
                    Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Center) {
                        AppText(text = it.name, fontSize = 16.sp, font = Res.font.nunito_semibold,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Start)

                        Row(modifier = Modifier.fillMaxWidth().padding(
                            top = 13.dp
                        )) {
                            AppText(text = "sas",
                                color = Color(0xFF8B96A6), fontSize = 12.sp,
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Start)
                            Image(imageVector = vectorResource(
                                Res.drawable.ic_right_arrow
                            ), contentDescription = null)
                        }
//                        AppText(text = it.name,
//                            textLines = 1,
//                            fontWeight = FontWeight.Medium,
//                            modifier = Modifier.fillMaxWidth()
//                            , textAlign = TextAlign.Start,
//                            fontSize = 16.sp)
//
//                        AppText(text = "${it.formattedDate}\nSize: ${it.readableSize}",
//                            fontWeight = FontWeight.Light,
//                            fontSize = 12.sp,
//                            textAlign = TextAlign.Start,
//                            color = MaterialTheme.colorScheme.onSurfaceVariant)
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
