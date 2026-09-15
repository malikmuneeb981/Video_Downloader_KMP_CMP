package org.example.project.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cyberarsenals.video.downloader.save.videos.domain.models.appmodels.HomeScreenUiState
import com.cyberarsenals.video.downloader.save.videos.domain.useCases.downloader.DownloadVideoUseCase
import io.ktor.client.call.body
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.domain.models.apiModels.DownloaderAPIResponse
import org.example.project.domain.models.appmodels.DownloaderEndpointModel

class HomeScreenViewModel(private val downloadVideoUseCase: DownloadVideoUseCase): ViewModel() {


    private val _uiState = MutableStateFlow(HomeScreenUiState())
    val uiState: StateFlow<HomeScreenUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<HomeScreenEvents>()
    val uiEvent = _uiEvent.receiveAsFlow()


    fun onEvent(event: HomeScreenViewModelEvents){
        when(event){
            is HomeScreenViewModelEvents.DownloadBtnClick -> {
                if (event.url == _uiState.value.lastRequestedUrl){
                    _uiState.update { it.copy(showBtmSheet = true) }
                }else{
                    getVideoDownloadUrl(url = event.url)
                }

            }
        }
    }
    fun getVideoDownloadUrl(url:String)= viewModelScope.launch(Dispatchers.IO)
    {

        try {
            _uiState.update {
                it.copy(isLoading = true)
            }
            val result = downloadVideoUseCase.invoke(vidUrl = url)

            // _downloaderResponseData.emit(Baseresponse.Loading())
            if (result.status.value == 200)
            {
                val responseBody: DownloaderAPIResponse = result.body()
                // _downloaderResponseData.emit(Baseresponse.Success(responseBody))
                _uiState.update {
                    it.copy(downloaderAPIResponse = responseBody, isLoading = false, showBtmSheet = true)
                }
            }
            else
            {
                _uiState.update {
                    it.copy(isLoading = false)
                }
                // _downloaderResponseData.emit(Baseresponse.Error(result.body()))
            }
        }catch (e:Exception)
        {
            println("HomeScreenViewModel Exception: ${e.message}")
            e.printStackTrace()
            _uiState.update {
                it.copy(isLoading = false)
            }
            // _downloaderResponseData.emit(Baseresponse.Error(e.toString()))
        }

    }

    fun dismissBtmSheet() {
        _uiState.update { it.copy(showBtmSheet = false) }
    }


    val downloaderEndpoints = listOf(
        DownloaderEndpointModel("facebook.com", "fbdownloader", 1),
        DownloaderEndpointModel("fb.watch", "fbdownloader", 1),
        DownloaderEndpointModel("instagram.com", "instadownloader", 1),
        DownloaderEndpointModel("twitter.com", "twdownloader", 1),
        DownloaderEndpointModel("x.com", "twdownloader", 1),
        DownloaderEndpointModel("threads.com", "thdownloader", 1),
        DownloaderEndpointModel("threads.net", "thdownloader", 1),
        DownloaderEndpointModel("snack.com", "snackdownloader", 1),
        DownloaderEndpointModel("snackvideo.com", "snackdownloader", 1),
        DownloaderEndpointModel("likee.com", "likeedownloader", 1),
        DownloaderEndpointModel("likee.video", "likeedownloader", 1),
        DownloaderEndpointModel("pinterest.com", "pinterestdownloader", 1),
        DownloaderEndpointModel("pin.it", "pinterestdownloader", 1),
        DownloaderEndpointModel("linkedin.com", "linkedindownloader", 1),
        DownloaderEndpointModel("dailymotion.com", "dailydownloader", 1),
        DownloaderEndpointModel("tiktok.com", "ttdownloader", 1)
    )

    fun checkIfEndPointIsValid(videoUrl: String): Boolean {
        for (i in downloaderEndpoints.indices) {
            if (videoUrl.contains(downloaderEndpoints[i].links)) {
                return true
            }
        }
        return false
    }


    sealed class HomeScreenViewModelEvents{
        data class DownloadBtnClick(val url: String): HomeScreenViewModelEvents()
    }
    sealed class HomeScreenEvents{
        // data object MakeApiCall: HomeScreenEvents()
        data class ShowBtmSheet(val downloaderAPIResponse: DownloaderAPIResponse?): HomeScreenEvents()
    }
}