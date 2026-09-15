package org.example.project.presentation.screens.trending


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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.LocalPlatformContext
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.cyberarsenals.video.downloader.save.videos.domain.models.apiModels.dailymotion.DailyMotionVideosResponse
import com.cyberarsenals.video.downloader.save.videos.domain.models.apiModels.dailymotion.DailyMotionVideosResponse.Category
import com.cyberarsenals.video.downloader.save.videos.presentation.composables.FeaturedVideosPager
import com.translate.speech.to.text.dictionary.instant.voice.translatoRes.presentation.composables.BottomNav
import com.translate.speech.to.text.dictionary.instant.voice.translator.presentation.composables.AppText
import downloaderkmpproductionapp.shared.generated.resources.Res
import downloaderkmpproductionapp.shared.generated.resources.all
import downloaderkmpproductionapp.shared.generated.resources.discover
import downloaderkmpproductionapp.shared.generated.resources.ic_play_short_reels
import downloaderkmpproductionapp.shared.generated.resources.ic_right_arrow_view_all
import downloaderkmpproductionapp.shared.generated.resources.ic_trendingsearch
import downloaderkmpproductionapp.shared.generated.resources.nunito_bold
import downloaderkmpproductionapp.shared.generated.resources.nunito_semibold
import downloaderkmpproductionapp.shared.generated.resources.search
import downloaderkmpproductionapp.shared.generated.resources.trending
import downloaderkmpproductionapp.shared.generated.resources.view_all
import kotlinx.coroutines.launch
import org.example.project.commons.BackHandler
import org.example.project.navigation.NavRoutes
import org.example.project.presentation.composables.HomeSecondaryTopBar
import org.example.project.presentation.composables.customShimmer
import org.example.project.presentation.viewModels.DailyMotionViewModel
import org.example.project.presentation.viewModels.DownloaderViewModel
import org.example.project.utils.Baseresponse
import org.example.project.utils.formatDurationHMS
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun TrendingScreen(
    navController: NavController,
    dailyMotionViewModel: DailyMotionViewModel,
    downloaderViewModel: DownloaderViewModel,
) {

    var searchText by remember { mutableStateOf("") }
    var isFocused by remember { mutableStateOf(false) }
    val placeHolder = stringResource(Res.string.search)
    val searchResponse by dailyMotionViewModel.dailymotionSearchedVideosResponseState.collectAsStateWithLifecycle()
    val dailyMotionResponse by dailyMotionViewModel.dailymotionVideosResponseState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        if (dailyMotionResponse.data?.categories==null){
            dailyMotionViewModel.onEvent(DailyMotionViewModel.TrendingScreenEvents.getVideos)
        }
    }
    BackHandler {
        navController.navigate(NavRoutes.DownloaderHomeScreen.route)
    }
    Scaffold(topBar = {
        Column(modifier = Modifier.fillMaxWidth()) {
            HomeSecondaryTopBar(navController = navController,stringResource(Res.string.discover),
                showBack = false)
        }
    }, bottomBar = {
        BottomNav(navController = navController)
    }, content = { paddingValues ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(
                top = paddingValues.calculateTopPadding(),
                bottom = (paddingValues.calculateBottomPadding() - 39.dp).coerceAtLeast(0.dp)
            ), horizontalAlignment = Alignment.CenterHorizontally) {
            BasicTextField(
                value = searchText,
                onValueChange = { searchText = it },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        // Do your search here
                        dailyMotionViewModel.getSearchedVideos(searchText)
                    }
                ),
                modifier = Modifier
                    .padding(top = 15.dp)
                    .padding(
                        horizontal = 16.dp
                    )
                    .background(Color.White, RoundedCornerShape(12.dp))
                    .border(
                        width = 1.dp, color = Color(0xFFECECEC),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(
                        vertical = 8.dp,
                        horizontal = 4.dp
                    )
                    .onFocusChanged { focusState -> isFocused = focusState.isFocused }
                    .fillMaxWidth(),
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 16.sp
                ),
                decorationBox =
                    {
                    innerTextField ->

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = 15.dp, end = 8.dp
                            )
                            .padding(vertical = 8.dp) // outer padding for icons/text
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {

                            when(val response = searchResponse){

                                is Baseresponse.Loading<*> -> {
                                    // Leading icon
                                    CircularProgressIndicator(modifier = Modifier.size(
                                        20.dp
                                    ), trackColor = Color.White,
                                        color = Color(0xFF1E4DFF),
                                        strokeWidth = 2.dp)
                                }
                                is Baseresponse.Success<*> -> {
                                    response.data?.let {
                                        downloaderViewModel.searchedVideosResponse = it
                                        navController.navigate(NavRoutes.SearchVideosScreen.route)
                                        dailyMotionViewModel.resetSearchResponse()
                                    }
                                }
                                else -> {
                                    // Leading icon
                                    Image(
                                        imageVector = vectorResource(Res.drawable.ic_trendingsearch),
                                        contentDescription = null,
                                        modifier = Modifier.clickable(
                                            indication = null,
                                            interactionSource = remember { MutableInteractionSource() },
                                            onClick = {
                                                if (searchText.isNotEmpty()){
                                                    dailyMotionViewModel.getSearchedVideos(searchText)
                                                }
                                            }
                                        )
                                    )
                                }
                            }


                            // Placeholder / Text
                            Box(modifier = Modifier
                                .weight(1f)
                                .padding(
                                    horizontal = 5.dp
                                )) {
                                if (searchText.isEmpty() && !isFocused) {
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
            when(val response = dailyMotionResponse){
                is Baseresponse.Error<*> -> {

                }
                is Baseresponse.IDLE<*> -> {

                }
                is Baseresponse.Loading<*> -> {

                    Column(modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator(modifier = Modifier.size(
                            70.dp
                        ), trackColor = Color.White,
                            color = Color(0xFF1E4DFF))
                    }
                }
                is Baseresponse.Success<*> -> {
                    FeaturedVideosPager(category = stringResource(Res.string.trending), items = response.data?.categories?.get(0)?.videos?:emptyList() , modifier = Modifier.padding(
                        top = 15.dp
                    ), navigateToWebView = {
                        downloaderViewModel.urlToLoad = it.videoUrl
                        navController.navigate(NavRoutes.WebViewScreen.route)
                    })
                    ScrollableCategoryTabs(
                        modifier = Modifier.weight(1f),
                       tabs =  listOf(stringResource(Res.string.all)) + (response.data?.categories?.mapNotNull { it.name } ?: emptyList()), response =  response.data?.categories?:emptyList(),
                        viewAllClick = {
                            downloaderViewModel.trendingItemSelected = it
                            navController.navigate(NavRoutes.ViewAllScreen.route)
                        }, navigateToVideoPlayer = {
                            downloaderViewModel.urlToLoad = it.videoUrl
                            navController.navigate(NavRoutes.WebViewScreen.route)
                        })

                }
            }



        }
    }, containerColor = Color.White)

}
@Composable
fun ScrollableCategoryTabs(modifier: Modifier= Modifier, tabs: List<String>, response: List<Category>, viewAllClick: (Category) -> Unit,
                           navigateToVideoPlayer:(DailyMotionVideosResponse.Video)-> Unit) {
    val pagerState = rememberPagerState(pageCount = { tabs.size })
    val coroutineScope = rememberCoroutineScope()
    Column(modifier = modifier) {
        ScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
            edgePadding = 1.dp,
            containerColor = Color.White,
            modifier = Modifier
                .padding(top = 30.dp),
            divider = {
                HorizontalDivider(
                    thickness = 1.dp,
                    color = Color.LightGray.copy(alpha = 0.5f),
                    modifier = Modifier
                )
            },
            indicator = { tabPositions ->
                TabRowDefaults.PrimaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                    height = 4.dp,
                    color = Color(0xFF1E4DFF),
                    shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)
                )
            }
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    selectedContentColor = Color(0xFF1E4DFF),
                    unselectedContentColor = Color(0xFF1F2937)
                ) {
                    AppText(
                        text = title,
                        fontSize = 16.sp,
                        font = Res.font.nunito_semibold,
                        color = if (pagerState.currentPage == index) Color(0xFF003BEE) else Color(0xFF1F2937),
                        modifier = Modifier.padding(vertical = 12.dp)
                    )
                }
            }
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            if (page == 0) {
                TrendingLazyColumnAll(items = response, viewAllClick = {
                    viewAllClick(it)
                }, videoItemClick = {
                    navigateToVideoPlayer(it)
                })
            } else {
                val categoryIndex = page - 1
                if (categoryIndex in response.indices) {
                    TrendingLazyColumnOthers(response[categoryIndex].videos ?: emptyList(),
                        videoItemClick = {
                           navigateToVideoPlayer(it)
                        })
                }
            }
        }
    }
}

@Composable
fun TrendingLazyColumnAll(items: List<Category>,
                          viewAllClick:(Category)-> Unit,
                          videoItemClick: (DailyMotionVideosResponse.Video) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        itemsIndexed(
            items = items,
            key = { _, item -> item.id.toString() }
        ) { index, item ->
            val (imageWidth, imageHeight) = when (index) {
                0 -> 132.dp to 132.dp
                1 -> 182.dp to 132.dp
                else -> 132.dp to 182.dp
            }

            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(top = 15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AppText(
                        text = item.name ?: "",
                        fontSize = 18.sp,
                        font = Res.font.nunito_bold,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Start
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {

                            items[index].videos?.let { viewAllClick(items[index]) }
                        }
                    ) {
                        AppText(
                            text = stringResource(Res.string.view_all),
                            color = Color(0xFF003BEE),
                            font = Res.font.nunito_semibold,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(end = 6.dp)
                        )

                        Image(
                            imageVector = vectorResource(Res.drawable.ic_right_arrow_view_all),
                            contentDescription = null
                        )
                    }
                }

                AllTrendingLazyRow(
                    item.videos ?: emptyList(),
                    imageWidth = imageWidth,
                    imageHeight = imageHeight,
                    videoItemClick = {
                        videoItemClick(it)
                    }
                )
            }
        }
    }
}

@Composable
fun TrendingLazyColumnOthers(items: List<DailyMotionVideosResponse.Video>,
                             videoItemClick: (DailyMotionVideosResponse.Video) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(
            10.dp
        )
    ) {
        items(
            items = items,
            key = { item -> item.id.toString() }
        ) { item ->
            TrendingVideoItem(video = item, modifier = Modifier.fillMaxWidth().height(200.dp),
                videoItemClick = {
                    videoItemClick(item)
                })
        }
    }
}

@Composable
fun AllTrendingLazyRow(
    items: List<DailyMotionVideosResponse.Video>,
    imageWidth: Dp,
    imageHeight: Dp,
    videoItemClick: (DailyMotionVideosResponse.Video) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .padding(start = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = items, key = { it.id.toString() }) {
            TrendingVideoItem(
                video = it,
                modifier = Modifier.size(width = imageWidth, height = imageHeight),
                videoItemClick = {
                    videoItemClick(it)
                }
            )
        }
    }
}

@Composable
fun TrendingVideoItem(
    video: DailyMotionVideosResponse.Video,
    modifier: Modifier = Modifier,
    videoItemClick:(DailyMotionVideosResponse.Video)-> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable {
                videoItemClick(video)
            }
            .background(Color(0xFFF3F4F6))
    ) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalPlatformContext.current)
                .data(video.thumbnail)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            loading = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .customShimmer()
                )
            }
        )

        // DARK GRADIENT OVERLAY (for play icon visibility)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.35f)
                        )
                    )
                )
        )

        // PLAY BUTTON
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .background(Color(0xFF1F2937).copy(alpha = 0.4f), CircleShape)
                .padding(all = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                imageVector = vectorResource(Res.drawable.ic_play_short_reels),
                contentDescription = null,
            )
        }
        
        Box(
            modifier = Modifier
                .padding(bottom = 8.dp, end = 8.dp)
                .align(Alignment.BottomEnd)
                .background(Color(0xFF1F2937).copy(alpha = 0.25f), CircleShape)
                .padding(horizontal = 4.dp, vertical = 2.dp),
            contentAlignment = Alignment.Center
        ) {
            AppText(
                text = formatDurationHMS(video.duration ?: 0),
                color = Color.White,
                fontSize = 10.sp
            )
        }
    }
}

@Preview
@Composable
private fun TrendingScreenPrev() {
    TrendingLazyColumnAll(
        items = listOf(
            Category(
                id = 1,
                name = "dasda",
                slug = "sdsds",
                order = 1,
                videos = listOf(
                    DailyMotionVideosResponse.Video(
                        id = "1",
                        title = "dssf",
                        thumbnail = "sdasdasd",
                        duration = 10,
                        views = 10,
                        videoUrl = "adsasdasd"
                    )
                )
            )
        ), viewAllClick = {

        }, videoItemClick = {

        }
    )
}