package com.search.lastfm.remote_repository.webservice.song

import com.search.lastfm.remote_repository.BaseRepository
import com.search.lastfm.remote_repository.webservice.song.entity.SongSearchApiResponse
import retrofit2.awaitResponse


class SongRepositoryImpl(private val songApi: SongApi) : BaseRepository() {

    suspend fun searchSong(
        method: String,
        track: String,
        apiKey: String,
        format: String
    ): SongSearchApiResponse? {
        return makeApiCall(
            call = {
                songApi.searchSong(
                    method,
                    track, apiKey, format
                ).awaitResponse()
            },
            errorMessage = "Error Fetching Song"
        )
    }
}