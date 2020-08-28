package com.search.lastfm.remote_repository.webservice.artist

import com.search.lastfm.remote_repository.BaseRepository
import com.search.lastfm.remote_repository.webservice.artist.entity.ArtistSearchApiResponse
import retrofit2.awaitResponse


class ArtistRepositoryImpl(private val artistApi: ArtistApi) : BaseRepository() {

    suspend fun searchArtist(
        method: String,
        artist: String,
        apiKey: String,
        format: String
    ): ArtistSearchApiResponse? {
        return makeApiCall(
            call = {
                artistApi.searchArtist(
                    method,
                    artist, apiKey, format
                ).awaitResponse()
            },
            errorMessage = "Error Fetching Artist"
        )
    }
}