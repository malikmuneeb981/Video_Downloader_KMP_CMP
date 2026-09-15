package org.example.project.di

import com.cyberarsenals.video.downloader.save.videos.data.dailymotion.DailyMotionApiService
import com.cyberarsenals.video.downloader.save.videos.data.dailymotion.DailyMotionRepoImpl
import com.cyberarsenals.video.downloader.save.videos.data.reels.ReelsApiRepoImpl
import com.cyberarsenals.video.downloader.save.videos.data.reels.ReelsApiService
import com.cyberarsenals.video.downloader.save.videos.domain.repositories.dailymotion.DailyMotionApiRepo
import com.cyberarsenals.video.downloader.save.videos.domain.repositories.downloader.DownloaderApiRepo
import com.cyberarsenals.video.downloader.save.videos.domain.repositories.reels.ReelsApiRepo
import com.cyberarsenals.video.downloader.save.videos.domain.useCases.dailymotion.GetDailyMotionVideosUseCase
import com.cyberarsenals.video.downloader.save.videos.domain.useCases.dailymotion.GetSearchedVideosUseCase
import com.cyberarsenals.video.downloader.save.videos.domain.useCases.downloader.DownloadVideoUseCase
import com.cyberarsenals.video.downloader.save.videos.domain.useCases.reels.GetReelsUseCase
import io.ktor.client.HttpClient
import org.example.project.data.DownloaderApiService
import org.example.project.data.DownloaderRepoImpl
import org.example.project.presentation.viewModels.LanguageSelectionViewModel
import org.example.project.presentation.viewModels.ReelsViewModel
import org.example.project.utils.DataStorePreferences
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val koinModule = module {
    single<DownloaderApiService>{
        DownloaderApiService(get<HttpClient>())
    }
    single<DownloaderApiRepo>{
        DownloaderRepoImpl(get<DownloaderApiService>())
    }
    single<DownloadVideoUseCase>{
        DownloadVideoUseCase(get<DownloaderApiRepo>())
    }
    single <DataStorePreferences>{
        DataStorePreferences(get())
    }
}
val reelsModule = module{
    single<ReelsApiService>{
        ReelsApiService(get<HttpClient>())
    }
    single<ReelsApiRepo>{
        ReelsApiRepoImpl(get<ReelsApiService>())
    }
    single<GetReelsUseCase>{
        GetReelsUseCase(get<ReelsApiRepo>())
    }
}
val languageModule = module{
    single<LanguageSelectionViewModel>{
        LanguageSelectionViewModel()
    }
}
val dailyMotionModule = module {
    single<DailyMotionApiService> {
        DailyMotionApiService(get<HttpClient>())
    }
    single<DailyMotionApiRepo> {
        DailyMotionRepoImpl(get<DailyMotionApiService>())
    }
    single<GetDailyMotionVideosUseCase> {
        GetDailyMotionVideosUseCase(get<DailyMotionApiRepo>())
    }
    single<GetSearchedVideosUseCase> {
        GetSearchedVideosUseCase(get<DailyMotionApiRepo>())
    }
}