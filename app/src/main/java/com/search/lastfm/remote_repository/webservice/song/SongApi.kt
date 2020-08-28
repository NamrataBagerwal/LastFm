package com.search.lastfm.remote_repository.webservice.song

import com.search.lastfm.remote_repository.webservice.song.entity.SongSearchApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

//http://ws.audioscrobbler.com/2.0/?method=track.search&track=believe&api_key=133a07d3689b4d2635303f5c8332b60e&format=json
interface SongApi {

    @GET("2.0")
    fun searchSong(
        @Query("method") method: String,
        @Query("track") track: String,
        @Query("api_key") apiKey: String,
        @Query("format") format: String
    ): Call<SongSearchApiResponse>
}