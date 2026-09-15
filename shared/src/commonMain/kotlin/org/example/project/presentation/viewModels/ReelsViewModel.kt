package org.example.project.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cyberarsenals.video.downloader.save.videos.domain.models.apiModels.reels.ReelsResponseItem
import com.cyberarsenals.video.downloader.save.videos.domain.useCases.reels.GetReelsUseCase
import io.ktor.client.call.body
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.project.utils.Baseresponse

class ReelsViewModel(
    private val getReelsUseCase: GetReelsUseCase,
//    private val saveLikedReelUseCase: SaveLikedReelUseCase,
//    private val getAllLikedReelUseCase: GetAllLikedReelUseCase,
//    private val saveBookmarkedReelUseCase: SaveBookmarkedReelUseCase,
//    private val getAllBookmarkedReelUseCase: GetAllBookmarkedReelUseCase,
//    private val deleteBookmarkedReelUseCase: DeleteBookmarkedReelByIdUseCase,
//    private val deleteLikedReelUseCase: DeleteLikedReelByIdUseCase,
): ViewModel() {

    private val _reelsResponse = MutableStateFlow<Baseresponse<List<ReelsResponseItem>>>(Baseresponse.Loading())
    val reelsResponse = _reelsResponse.asStateFlow()
//    private val _likedReels = MutableStateFlow< Baseresponse<List<LikedReels>>>(Baseresponse.IDLE())
//    val likedReels = _likedReels.asStateFlow()
//    private val _bookmarkedReels = MutableStateFlow<List<BookmarkedReels>>(emptyList())
//    val bookmarkedReels = _bookmarkedReels.asStateFlow()

    fun onEvent(event:ReelsViewModelEvents){
        when(event){
            ReelsViewModelEvents.GetReelsFromApi -> {
                if (_reelsResponse.value.data.isNullOrEmpty()){
                    getReels()
                }
            }
//            is ReelsViewModelEvents.SaveLikedReel->{
//                saveLikedReelInRoomDb(event.likedReels)
//            }
//            ReelsViewModelEvents.GetAllLikedReel->{
//                getAllLikedReels()
//            }
//            is ReelsViewModelEvents.SaveBookmarkedReel->{
//                saveBookmarkedReelInRoomDb(event.bookmarkedReels)
//            }
//            ReelsViewModelEvents.GetAllBookmarkedReel->{
//                getAllBookmarkedReels()
//            }
//            is ReelsViewModelEvents.DeleteBookmarkedReel -> {
//                deleteBookmarkedReelInRoomDb(event.url)
//            }
//            is ReelsViewModelEvents.DeleteLikedReel -> {
//                deleteLikedReelInRoomDb(event.url)
//            }
        }
    }
//    private fun saveLikedReelInRoomDb(likedReels: LikedReels)=viewModelScope.launch(Dispatchers.IO) {
//        saveLikedReelUseCase.invoke(likedReels = likedReels)
//    }
//    private fun deleteLikedReelInRoomDb(url: String)=viewModelScope.launch(Dispatchers.IO) {
//        deleteLikedReelUseCase.invoke(url = url)
//    }
//    private fun deleteBookmarkedReelInRoomDb(url: String)=viewModelScope.launch(Dispatchers.IO) {
//        deleteBookmarkedReelUseCase.invoke(url = url)
//    }
//    private fun saveBookmarkedReelInRoomDb(bookmarkedReels: BookmarkedReels)=viewModelScope.launch(Dispatchers.IO) {
//        saveBookmarkedReelUseCase.invoke(bookmarkedReels = bookmarkedReels)
//    }

    private fun getReels() = viewModelScope.launch(Dispatchers.IO)
    {

        try {
            val result = getReelsUseCase.invoke()
            _reelsResponse.emit(Baseresponse.Loading())
            if (result.status.value == 200)
            {
                _reelsResponse.emit(Baseresponse.Success(result.body<List<ReelsResponseItem>>()))
            }
            else
            {
                _reelsResponse.emit(Baseresponse.Error(result.body()))
            }
        }catch (e:Exception)
        {
            _reelsResponse.emit(Baseresponse.Error(e.toString()))
        }

    }
//    private fun getAllLikedReels() = viewModelScope.launch {
//        _likedReels.emit(Baseresponse.Loading())
//        getAllLikedReelUseCase().collect {
//            if (it.isEmpty()){
//                _likedReels.emit(Baseresponse.Error("No Reels Found"))
//            }else{
//                _likedReels.emit(Baseresponse.Success(it))
//            }
//            // _likedReels.emit(it)
//        }
//    }
//    private fun getAllBookmarkedReels() = viewModelScope.launch {
//        getAllBookmarkedReelUseCase().collect {
//            _bookmarkedReels.emit(it)
//        }
//    }



    sealed interface ReelsViewModelEvents{
        data object GetReelsFromApi:ReelsViewModelEvents
//        data class SaveLikedReel(val likedReels: LikedReels): ReelsViewModelEvents
//        data object GetAllLikedReel: ReelsViewModelEvents
//
//        data class DeleteLikedReel(val url: String): ReelsViewModelEvents
//
//        data class SaveBookmarkedReel(val bookmarkedReels: BookmarkedReels): ReelsViewModelEvents
//
//        data object GetAllBookmarkedReel: ReelsViewModelEvents
//
//        data class DeleteBookmarkedReel(val url: String): ReelsViewModelEvents
    }
    sealed interface ReelsScreenEvents{
        //data object ShowReels:ReelsScreenEvents
    }


}