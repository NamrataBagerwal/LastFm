package com.search.lastfm.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.search.lastfm.dto.SongDto
import com.search.lastfm.usecase.song.SongUsecase
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class SongViewModel(private val songUsecase: SongUsecase) : ViewModel() {
    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + viewModelScope.coroutineContext + Dispatchers.IO

    private val songLiveData: MutableLiveData<List<SongDto>> by lazy {
        MutableLiveData<List<SongDto>>()
    }

    private fun searchSong(song: String) {
        viewModelScope.launch(context = coroutineContext) {
            val songList = songUsecase.searchSong(song)
            songLiveData.postValue(songList)
        }
    }

    fun getSongLiveData(): LiveData<List<SongDto>> {
        return songLiveData
    }

    fun updateSongLiveData(song: String) {
        searchSong(song)
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