package com.search.lastfm.usecase

import com.search.lastfm.AppConstants
import com.search.lastfm.BuildConfig
import com.search.lastfm.dto.ArtistDto
import com.search.lastfm.remote_repository.BaseRepository
import com.search.lastfm.remote_repository.webservice.artist.ArtistRepositoryImpl
import com.search.lastfm.remote_repository.webservice.artist.entity.Artist
import com.search.lastfm.remote_repository.webservice.artist.entity.ArtistSearchApiResponse

class ArtistUsecase(private val artistRepositoryImpl: BaseRepository) {
    suspend fun searchArtist(artist: String): List<ArtistDto> {
        return convertToDto(
            (artistRepositoryImpl as ArtistRepositoryImpl).searchArtist(
                AppConstants.ARTIST_SEARCH,
                artist,
                BuildConfig.API_KEY,
                AppConstants.JSON_FORMAT
            )
        )
    }

    private fun convertToDto(artistSearchApiResponse: ArtistSearchApiResponse?): List<ArtistDto> {
        val artistList = mutableListOf<ArtistDto>()
        artistSearchApiResponse?.results?.artistmatches?.artist?.forEach { artist: Artist ->

            val artistDto = ArtistDto(
                artist.image[AppConstants.INDEX_ZERO].text,
                artist.listeners,
                artist.mbid,
                artist.name,
                artist.streamable,
                artist.url
            )

            artistList.add(artistDto)

        }
        return artistList
    }
}