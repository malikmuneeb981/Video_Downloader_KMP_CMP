package org.example.project

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.painterResource

import downloaderkmpproductionapp.shared.generated.resources.Res
import downloaderkmpproductionapp.shared.generated.resources.compose_multiplatform
import org.example.project.commons.provideApiModule
import org.example.project.commons.provideAppUtilFunctionsModule
import org.example.project.commons.provideDataStorePref
import org.example.project.commons.provideDownloadManagerModule
import org.example.project.commons.provideFilesManagerModule
import org.example.project.commons.provideViewModelsModule
import org.example.project.di.dailyMotionModule
import org.example.project.di.koinModule
import org.example.project.di.languageModule
import org.example.project.di.reelsModule
import org.example.project.presentation.navigation.NavGraph
import org.example.project.presentation.viewModels.DailyMotionViewModel
import org.example.project.presentation.viewModels.DownloaderViewModel
import org.example.project.presentation.viewModels.MediaReaderViewModel
import org.example.project.presentation.viewModels.ReelsViewModel
import org.koin.compose.KoinApplication
import org.koin.compose.getKoin
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.KoinApplication

import org.koin.core.module.Module
import org.koin.dsl.koinConfiguration
import org.koin.dsl.module

import coil3.compose.setSingletonImageLoaderFactory
import org.example.project.commons.getPlatformImageLoader
import org.example.project.commons.provideDownloadStatusModule

@Composable
@Preview
fun App(platformModule: Module = module { }) {

    setSingletonImageLoaderFactory { context ->
        getPlatformImageLoader(context)
    }

    KoinApplication(configuration = koinConfiguration(declaration = {
        modules(
            koinModule,
            reelsModule,
            languageModule,
            dailyMotionModule,
            provideAppUtilFunctionsModule(),
            provideApiModule(),
            provideViewModelsModule(),
            provideDataStorePref(),
            platformModule,
            provideDownloadManagerModule(),
            provideFilesManagerModule(),
            provideDownloadStatusModule()
        )
    }), content = {

        val downloaderViewModel = koinViewModel<DownloaderViewModel>()
        val reelsViewModel = koinViewModel<ReelsViewModel>()
        val dailyMotionViewModel = koinViewModel<DailyMotionViewModel>()
        val mediaReaderViewModel = koinViewModel<MediaReaderViewModel>()
        NavGraph(
            downloaderViewModel = downloaderViewModel,
            reelsViewModel = reelsViewModel,
            dailyMotionViewModel = dailyMotionViewModel,
            mediaReaderViewModel = mediaReaderViewModel
        )
    })
}