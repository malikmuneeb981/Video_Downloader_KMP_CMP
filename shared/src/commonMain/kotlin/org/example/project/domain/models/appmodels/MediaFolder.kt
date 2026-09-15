package org.example.project.domain.models.appmodels

data class MediaFolder(
    val folderName: String,
    val files: MutableList<MediaFile>
)