package com.search.lastfm.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.search.lastfm.dto.AlbumDto
import com.search.lastfm.usecase.album.AlbumUsecase
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class AlbumViewModel(private val albumUsecase: AlbumUsecase): ViewModel() {
    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + viewModelScope.coroutineContext + Dispatchers.IO

    private val albumLiveData: MutableLiveData<List<AlbumDto>> by lazy {
        MutableLiveData<List<AlbumDto>>()
    }

    private fun searchAlbum(album:String) {
        viewModelScope.launch(context = coroutineContext) {
            val album = albumUsecase.searchAlbum(album)
            albumLiveData.postValue(album)
        }
    }

    fun getAlbumLiveData(): LiveData<List<AlbumDto>> {
        return albumLiveData
    }

    fun updateAlbumLiveData(album:String) {
        searchAlbum(album)
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