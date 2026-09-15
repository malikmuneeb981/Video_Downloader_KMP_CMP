package org.example.project.commons

import org.example.project.domain.models.appmodels.MediaFile
import org.example.project.domain.models.appmodels.MediaFolder
import org.koin.core.module.Module

interface FileManager {
    suspend fun loadMedia(): Map<String, MediaFolder>
    suspend fun loadVideos(): List<MediaFile>
}

expect fun provideFilesManagerModule(): Module