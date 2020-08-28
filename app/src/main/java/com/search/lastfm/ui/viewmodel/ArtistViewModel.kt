package com.search.lastfm.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.search.lastfm.dto.ArtistDto
import com.search.lastfm.usecase.artist.ArtistUsecase
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ArtistViewModel(private val artistUsecase: ArtistUsecase) : ViewModel() {
    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + viewModelScope.coroutineContext + Dispatchers.IO

    private val artistLiveData: MutableLiveData<List<ArtistDto>> by lazy {
        MutableLiveData<List<ArtistDto>>()
    }

    private fun searchArtist(artist: String) {
        viewModelScope.launch(context = coroutineContext) {
            val artist = artistUsecase.searchArtist(artist)
            artistLiveData.postValue(artist)
        }
    }

    fun getArtistLiveData(): LiveData<List<ArtistDto>> {
        return artistLiveData
    }

    fun updateArtistLiveData(artist: String) {
        searchArtist(artist)
    }

    override fun onCleared() {
        super.onCleared()
        if (coroutineContext.isActive) {
            coroutineContext.cancelChildren()
        }
        if (parentJob.isActive) {
            parentJob.cancel()
        }
    }
}