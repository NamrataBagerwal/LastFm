package com.search.lastfm.remote_repository.webservice.album

import com.search.lastfm.remote_repository.BaseRepository
import com.search.lastfm.remote_repository.webservice.album.entity.AlbumSearchApiResponse
import retrofit2.awaitResponse


class AlbumRepositoryImpl(private val albumApi: AlbumApi) : BaseRepository() {

    suspend fun searchAlbum(
        method: String,
        album: String,
        apiKey: String,
        format: String
    ): AlbumSearchApiResponse? {
        return makeApiCall(
            call = {
                albumApi.searchAlbum(
                    method,
                    album, apiKey, format
                ).awaitResponse()
            },
            errorMessage = "Error Fetching Album"
        )
    }
}