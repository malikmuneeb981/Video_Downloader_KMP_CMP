package org.example.project.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.project.commons.FileManager
import org.example.project.domain.models.appmodels.MediaFile
import org.example.project.domain.models.appmodels.MediaFolder

class MediaReaderViewModel(
    private val fileManager: FileManager
): ViewModel() {


    private val _mediaFolders = MutableStateFlow<Map<String, MediaFolder>>(emptyMap())
    val mediaFolders = _mediaFolders.asStateFlow()

    private val _videoList = MutableStateFlow<List<MediaFile>>(emptyList())
    val videoList = _videoList.asStateFlow()


    fun loadMedia() = viewModelScope.launch(Dispatchers.IO) {
            val foldersMap = fileManager.loadMedia()
            _mediaFolders.value = foldersMap
    }


    fun loadVideos()= viewModelScope.launch(Dispatchers.IO) {
            val videoList = fileManager.loadVideos()
            _videoList.value = videoList
    }


}