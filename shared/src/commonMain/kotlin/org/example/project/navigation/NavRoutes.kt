package org.example.project.navigation

sealed class NavRoutes(val route: String) {


    //Downloader Screens
    data object DownloaderHomeScreen: NavRoutes("DownloaderHomeScreen")

    data object DownloaderSpecialScreen: NavRoutes("DownloaderSpecialScreen")

    data object TrendingHomeScreen: NavRoutes("TrendingHomeScreen")

    data object ViewAllScreen: NavRoutes("ViewAllScreen")
    data object SearchVideosScreen: NavRoutes("SearchVideosScreen")
    data object SplashScreen: NavRoutes("SplashScreen")
    data object DownloadsScreen: NavRoutes("DownloadsScreen")

    data object StatusSaverHomeScreen: NavRoutes("StatusSaverHomeScreen")

    data object ImageViewScreen: NavRoutes("ImageViewScreen")

    data object VideoPlayerScreen: NavRoutes("VideoPlayerScreen")

    //Home Screens

    data object PlayerHomeScreen: NavRoutes("PlayerHomeScreen")
    data object NativeVideoPlayerScreen: NavRoutes("NativeVideoPlayerScreen")
    data object VideosInsideFolderScreen: NavRoutes("VideoInsideFolderScreen")

    data object ReelsScreen: NavRoutes("ReelsScreen")

    data object ReelsHomeScreen: NavRoutes("ReelsHomeScreen")

    data object FavReelsScreen: NavRoutes("FavReelsScreen")
    data object FavReelsPlayScreen: NavRoutes("FavReelsPlayScreen")

    //Music Player Screens

    data object MusicPlayerHomeScreen: NavRoutes("MusicPlayerHomeScreen")

    //More Screens

    data object MoreScreen: NavRoutes("MoreScreen")

    //Language Screen
    data object LanguageScreen: NavRoutes("LanguageScreen")
    data object LanguageScreenInApp: NavRoutes("LanguageScreenInApp")
    data object SelectFeatureScreen: NavRoutes("SelectFeatureScreen")

    data object OnBoardingScreen: NavRoutes("OnBoardingScreen")

    data object PremiumScreen: NavRoutes("PremiumScreen")

    data object WebViewScreen: NavRoutes("WebViewScreen")
    data object ExitScreen: NavRoutes("ExitScreen")
    data object FeedBackScreen: NavRoutes("FeedBackScreen")
}