package com.search.lastfm.usecase

import com.search.lastfm.AppConstants
import com.search.lastfm.BuildConfig
import com.search.lastfm.dto.AlbumDto
import com.search.lastfm.remote_repository.BaseRepository
import com.search.lastfm.remote_repository.webservice.album.AlbumRepositoryImpl
import com.search.lastfm.remote_repository.webservice.album.entity.Album
import com.search.lastfm.remote_repository.webservice.album.entity.AlbumSearchApiResponse

class AlbumUsecase(private val albumRepositoryImpl: BaseRepository) {
    suspend fun searchAlbum(album: String): List<AlbumDto> {
        return convertToDto(
            (albumRepositoryImpl as AlbumRepositoryImpl).searchAlbum(
                AppConstants.ALBUM_SEARCH,
                album,
                BuildConfig.API_KEY,
                AppConstants.JSON_FORMAT
            )
        )
    }

    private fun convertToDto(albumSearchApiResponse: AlbumSearchApiResponse?): List<AlbumDto> {
        val albumList = mutableListOf<AlbumDto>()
        albumSearchApiResponse?.results?.albummatches?.album?.forEach { album: Album ->

            val albumDto = AlbumDto(
                album.artist,
                album.image[AppConstants.INDEX_ZERO].text,
                album.mbid,
                album.name,
                album.streamable,
                album.url
            )

            albumList.add(albumDto)

        }
        return albumList
    }
}