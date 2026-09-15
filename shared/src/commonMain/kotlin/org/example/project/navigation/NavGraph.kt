package org.example.project.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cyberarsenals.video.downloader.save.videos.presentation.screens.more.MoreScreen
import org.example.project.navigation.NavRoutes
import org.example.project.presentation.screens.downloader.DownloaderHomeScreen
import org.example.project.presentation.screens.downloader.DownloadsScreen
import org.example.project.presentation.screens.language.LanguageSelection
import org.example.project.presentation.screens.language.LanguageSelectionInApp
import org.example.project.presentation.screens.onBoarding.OnBoardingScreen
import org.example.project.presentation.screens.player.PlayerHomeScreen
import org.example.project.presentation.screens.player.VideoPlayerForMedia
import org.example.project.presentation.screens.player.VideosInsideFolderScreen
import org.example.project.presentation.screens.reels.ReelsHomeScreen
import org.example.project.presentation.screens.splash.SplashScreen
import org.example.project.presentation.screens.trending.SearchVideosScreen
import org.example.project.presentation.screens.trending.TrendingScreen
import org.example.project.presentation.screens.trending.ViewAllScreen
import org.example.project.presentation.screens.trending.WebViewScreen
import org.example.project.presentation.screens.videoPlayer.VideoPlayerScreen
import org.example.project.presentation.viewModels.DailyMotionViewModel
import org.example.project.presentation.viewModels.DownloaderViewModel
import org.example.project.presentation.viewModels.HomeScreenViewModel
import org.example.project.presentation.viewModels.LanguageSelectionViewModel
import org.example.project.presentation.viewModels.MediaReaderViewModel
import org.example.project.presentation.viewModels.ReelsViewModel
import org.koin.compose.getKoin
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun NavGraph(
    downloaderViewModel: DownloaderViewModel,
    reelsViewModel: ReelsViewModel,
    mediaReaderViewModel: MediaReaderViewModel,
    dailyMotionViewModel : DailyMotionViewModel
){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavRoutes.SplashScreen.route){

        composable(NavRoutes.DownloaderHomeScreen.route) {
            val homeScreenViewModel =  koinViewModel<HomeScreenViewModel>()
            DownloaderHomeScreen(navController = navController
                ,downloaderViewModel = downloaderViewModel,
                homeScreenViewModel = homeScreenViewModel,
                reelsViewModel = reelsViewModel
            )
        }
//        composable(NavRoutes.DownloaderSpecialScreen.route) {
//            val homeScreenViewModel =  koinViewModel<HomeScreenViewModel>()
//            DownloaderSpecialScreen(navController = navController
//                ,downloaderViewModel = downloaderViewModel,
//                homeScreenViewModel = homeScreenViewModel,
//                remoteConfigViewModel = remoteConfigViewModel,
//            )
//        }
        composable(NavRoutes.SplashScreen.route) {
            SplashScreen(navController = navController, downloaderViewModel = downloaderViewModel)
        }
        composable(NavRoutes.TrendingHomeScreen.route) {
            TrendingScreen(
                navController = navController,
                dailyMotionViewModel = dailyMotionViewModel,
                downloaderViewModel = downloaderViewModel,
            )
        }
        composable(NavRoutes.ViewAllScreen.route) {
            ViewAllScreen(
                navController = navController,
                downloaderViewModel = downloaderViewModel
            )
        }
        composable(NavRoutes.SearchVideosScreen.route) {
            SearchVideosScreen(
                navController = navController,
                downloaderViewModel = downloaderViewModel
            )
        }
        composable(NavRoutes.DownloadsScreen.route) {
            DownloadsScreen(navController= navController,downloaderViewModel =  downloaderViewModel)
        }
        composable(NavRoutes.VideoPlayerScreen.route) {
            VideoPlayerScreen(navController= navController, downloaderViewModel =  downloaderViewModel)
        }
        composable(NavRoutes.NativeVideoPlayerScreen.route) {
            VideoPlayerForMedia(navController= navController, downloaderViewModel =  downloaderViewModel,
                mediaReaderViewModel = mediaReaderViewModel)
        }
        composable(NavRoutes.PlayerHomeScreen.route) {
            PlayerHomeScreen(navController= navController, downloaderViewModel =  downloaderViewModel,
                mediaReaderViewModel=mediaReaderViewModel)
        }
//        composable(NavRoutes.HomeScreen.route) {
//
//            HomeScreen(navController= navController, downloaderViewModel = downloaderViewModel,
//                mediaReaderViewModel = mediaReaderViewModel, remoteConfigViewModel = remoteConfigViewModel)
//        }
        composable(NavRoutes.VideosInsideFolderScreen.route) {
            VideosInsideFolderScreen(navController=navController,
                downloaderViewModel = downloaderViewModel)
        }
//        composable(NavRoutes.ReelsScreen.route) {
//           // val reelsViewModel =  koinViewModel<ReelsViewModel>()
//            ReelsScreen(navController= navController, downloaderViewModel = downloaderViewModel,
//                reelsViewModel = reelsViewModel)
//        }
        composable(NavRoutes.ReelsHomeScreen.route) {
            val reelsViewModel =  koinViewModel<ReelsViewModel>()
            ReelsHomeScreen(navController= navController, downloaderViewModel = downloaderViewModel,
                reelsViewModel = reelsViewModel)
        }
//        composable(NavRoutes.FavReelsScreen.route) {
//           // val reelsViewModel =  koinViewModel<ReelsViewModel>()
//            FavReelsScreen(navController= navController, downloaderViewModel = downloaderViewModel,
//                reelsViewModel = reelsViewModel, remoteConfigViewModel = remoteConfigViewModel)
//        }
//        composable(NavRoutes.FavReelsPlayScreen.route) {
//            //val reelsViewModel =  koinViewModel<ReelsViewModel>()
//            FavReelsPlayScreen(navController= navController, downloaderViewModel = downloaderViewModel,
//                reelsViewModel = reelsViewModel)
//        }
//        composable(NavRoutes.MusicPlayerHomeScreen.route) {
//            MusicPlayerHomeScreen(navController= navController,
//                remoteConfigViewModel = remoteConfigViewModel)
//        }
        composable(NavRoutes.MoreScreen.route) {
            val languageSelectionViewModel =  koinViewModel<LanguageSelectionViewModel>()
            MoreScreen(navController= navController,
                downloaderViewModel = downloaderViewModel,
                languageSelectionViewModel = languageSelectionViewModel
            )
        }
//        composable(NavRoutes.StatusSaverHomeScreen.route) {
//            StatusSaverHomeScreen(navController= navController,downloaderViewModel=downloaderViewModel,
//                remoteConfigViewModel = remoteConfigViewModel)
//        }
//        composable(NavRoutes.ImageViewScreen.route) {
//            ImageViewScreen(navController= navController,downloaderViewModel=downloaderViewModel,
//                remoteConfigViewModel = remoteConfigViewModel)
//        }
//        composable(NavRoutes.SelectFeatureScreen.route) {
//            SelectFeatureScreen(navController = navController)
//        }
        composable(NavRoutes.OnBoardingScreen.route) {
            OnBoardingScreen(navController = navController)
        }
        composable(NavRoutes.LanguageScreen.route) {
            val languageSelectionViewModel =  koinViewModel<LanguageSelectionViewModel>()
            LanguageSelection(navController= navController, languageSelectionViewModel = languageSelectionViewModel,
                downloaderViewModel = downloaderViewModel)
        }
        composable(NavRoutes.LanguageScreenInApp.route) {
            val languageSelectionViewModel =  koinViewModel<LanguageSelectionViewModel>()
            LanguageSelectionInApp(navController= navController, languageSelectionViewModel = languageSelectionViewModel,
                downloaderViewModel = downloaderViewModel)
        }
//        composable(NavRoutes.PremiumScreen.route) {
//            PremiumScreen(
//                inAppPurchaseClass = inAppPurchaseClass,
//                navController= navController,
//                downloaderViewModel = downloaderViewModel,
//                remoteConfigViewModel = remoteConfigViewModel
//              )
//        }
        composable(NavRoutes.WebViewScreen.route) {
            WebViewScreen(
                //inAppPurchaseClass = inAppPurchaseClass,
                navController= navController,
                downloaderViewModel = downloaderViewModel
              )
        }
//        composable(NavRoutes.ExitScreen.route) {
//            ExitScreen(
//                navController= navController,
//                remoteConfigViewModel = remoteConfigViewModel
//              )
//        }
//        composable(NavRoutes.FeedBackScreen.route) {
//            FeedBackScreen(
//                navController= navController,
//                remoteConfigViewModel = remoteConfigViewModel
//              )
//        }


    }

}