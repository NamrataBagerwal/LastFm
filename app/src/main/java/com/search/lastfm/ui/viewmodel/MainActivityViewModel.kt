package com.search.lastfm.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.search.lastfm.dto.AlbumDto
import com.search.lastfm.dto.ArtistDto
import com.search.lastfm.dto.SongDto
import com.search.lastfm.usecase.album.AlbumUsecase
import com.search.lastfm.usecase.artist.ArtistUsecase
import com.search.lastfm.usecase.song.SongUsecase
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivityViewModel(
    private val artistUsecase: ArtistUsecase,
    private val albumUsecase: AlbumUsecase,
    private val songUsecase: SongUsecase
) : ViewModel() {
    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + viewModelScope.coroutineContext + Dispatchers.IO

    private val artistLiveData: MutableLiveData<List<ArtistDto>> by lazy {
        MutableLiveData<List<ArtistDto>>()
    }

    private val albumLiveData: MutableLiveData<List<AlbumDto>> by lazy {
        MutableLiveData<List<AlbumDto>>()
    }

    private val songLiveData: MutableLiveData<List<SongDto>> by lazy {
        MutableLiveData<List<SongDto>>()
    }

    fun getArtistLiveData(): LiveData<List<ArtistDto>> {
        return artistLiveData
    }

    fun updateArtistLiveData(artist: String) {
        searchArtist(artist)
    }

    private fun searchArtist(artist: String) {
        viewModelScope.launch(context = coroutineContext) {
            val artistList = artistUsecase.searchArtist(artist)
            artistLiveData.postValue(artistList)
        }
    }

    fun getAlbumLiveData(): LiveData<List<AlbumDto>> {
        return albumLiveData
    }

    fun updateAlbumLiveData(album: String) {
        searchAlbum(album)
    }

    private fun searchAlbum(album: String) {
        viewModelScope.launch(context = coroutineContext) {
            val albumList = albumUsecase.searchAlbum(album)
            albumLiveData.postValue(albumList)
        }
    }

    fun getSongLiveData(): LiveData<List<SongDto>> {
        return songLiveData
    }

    fun updateSongLiveData(song: String) {
        searchSong(song)
    }

    private fun searchSong(song: String) {
        viewModelScope.launch(context = coroutineContext) {
            val songList = songUsecase.searchSong(song)
            songLiveData.postValue(songList)
        }
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