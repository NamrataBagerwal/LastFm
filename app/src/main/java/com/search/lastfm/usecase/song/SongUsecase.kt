package com.search.lastfm.usecase.song

import com.search.lastfm.AppConstants
import com.search.lastfm.dto.SongDto
import com.search.lastfm.remote_repository.BaseRepository
import com.search.lastfm.remote_repository.webservice.song.SongRepositoryImpl
import com.search.lastfm.remote_repository.webservice.song.entity.SongSearchApiResponse
import com.search.lastfm.remote_repository.webservice.song.entity.Song

class SongUsecase(private val songRepositoryImpl: BaseRepository) {

    suspend fun searchSong(song: String): List<SongDto> {
        return convertToDto(
            (songRepositoryImpl as SongRepositoryImpl).searchSong(
                AppConstants.SONG_SEARCH,
                song,
                AppConstants.API_KEY,
                AppConstants.JSON_FORMAT
            )
        )
    }

    private fun convertToDto(songSearchApiResponse: SongSearchApiResponse?): List<SongDto> {
        val songList = mutableListOf<SongDto>()
        songSearchApiResponse?.results?.trackMatches?.track?.forEach { song: Song ->

            val songDto = SongDto(
                song.artist,
                song.image[AppConstants.INDEX_ONE].text,
                song.listeners,
                song.mbid,
                song.name,
                song.streamable,
                song.url
            )

            songList.add(songDto)

        }
        return songList
    }
}