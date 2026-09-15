package org.example.project.commons

import org.example.project.presentation.viewModels.DailyMotionViewModel
import org.example.project.presentation.viewModels.DownloaderViewModel
import org.example.project.presentation.viewModels.HomeScreenViewModel
import org.example.project.presentation.viewModels.MediaReaderViewModel
import org.example.project.presentation.viewModels.ReelsViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual fun provideViewModelsModule(): Module {
    return module {
        singleOf(::DownloaderViewModel)
        singleOf(::HomeScreenViewModel)
        singleOf(::ReelsViewModel)
        singleOf(::DailyMotionViewModel)
        singleOf(::MediaReaderViewModel)
    }
}