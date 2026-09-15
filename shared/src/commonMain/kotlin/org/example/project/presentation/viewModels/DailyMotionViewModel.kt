package org.example.project.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cyberarsenals.video.downloader.save.videos.domain.models.apiModels.dailymotion.DailyMotionSearchResponse
import com.cyberarsenals.video.downloader.save.videos.domain.models.apiModels.dailymotion.DailyMotionVideosResponse
import com.cyberarsenals.video.downloader.save.videos.domain.useCases.dailymotion.GetDailyMotionVideosUseCase
import com.cyberarsenals.video.downloader.save.videos.domain.useCases.dailymotion.GetSearchedVideosUseCase
import io.ktor.client.call.body
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.example.project.utils.Baseresponse

class DailyMotionViewModel(private val getDailyMotionVideosUseCase: GetDailyMotionVideosUseCase,
                           private val getDailyMotionSearchedVideosUseCase: GetSearchedVideosUseCase): ViewModel() {

    private val _dailymotionVideosResponseState = MutableStateFlow<Baseresponse<DailyMotionVideosResponse>>(
        Baseresponse.Loading())
    val dailymotionVideosResponseState: StateFlow<Baseresponse<DailyMotionVideosResponse>> get() = _dailymotionVideosResponseState

    private val _dailymotionSearchedVideosResponseState = MutableStateFlow<Baseresponse<DailyMotionSearchResponse>>(
        Baseresponse.IDLE())
    val dailymotionSearchedVideosResponseState: StateFlow<Baseresponse<DailyMotionSearchResponse>> get() = _dailymotionSearchedVideosResponseState


    fun onEvent(event: TrendingScreenEvents){
        when(event){
            TrendingScreenEvents.getVideos -> {
                getVideosByCategory()
            }
        }
    }
    fun getVideosByCategory()= viewModelScope.launch(Dispatchers.IO)
    {

        try {
            _dailymotionVideosResponseState.emit(Baseresponse.Loading())
            val result = getDailyMotionVideosUseCase.invoke()
            if (result.status.value == 200)
            {
                _dailymotionVideosResponseState.emit(Baseresponse.Success(result.body()))
               // Log.d("Downloader Response",result.body())
            }
            else
            {
                _dailymotionVideosResponseState.emit(Baseresponse.Error("Something Went Wrong ${result.status.value}"))
                // _downloaderResponseData.emit(Baseresponse.Error(result.body()))
            }
        }catch (e:Exception)
        {
            _dailymotionVideosResponseState.emit(Baseresponse.Error("Something Went Wrong"))
            // _downloaderResponseData.emit(Baseresponse.Error(e.toString()))
        }

    }
    fun getSearchedVideos(keyword: String)= viewModelScope.launch(Dispatchers.IO)
    {

        try {
            _dailymotionSearchedVideosResponseState.emit(Baseresponse.Loading())
            val result = getDailyMotionSearchedVideosUseCase.invoke(keyword = keyword)
            if (result.status.value == 200)
            {
                _dailymotionSearchedVideosResponseState.emit(Baseresponse.Success(result.body()))
               // Log.d("Downloader Response",result.body())
            }
            else
            {
                _dailymotionSearchedVideosResponseState.emit(Baseresponse.Error("Something Went Wrong ${result.status.value}"))
                // _downloaderResponseData.emit(Baseresponse.Error(result.body()))
            }
        }catch (e:Exception)
        {
            _dailymotionSearchedVideosResponseState.emit(Baseresponse.Error("Something Went Wrong"))
            // _downloaderResponseData.emit(Baseresponse.Error(e.toString()))
        }

    }
    fun resetSearchResponse()= viewModelScope.launch(Dispatchers.IO){
        _dailymotionSearchedVideosResponseState.emit(Baseresponse.IDLE())
    }

    sealed interface TrendingScreenEvents{
        data object getVideos:TrendingScreenEvents
    }


}