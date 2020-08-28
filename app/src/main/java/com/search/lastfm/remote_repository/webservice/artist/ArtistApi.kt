package com.search.lastfm.remote_repository.webservice.artist

import com.search.lastfm.remote_repository.webservice.artist.entity.ArtistSearchApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

//http://ws.audioscrobbler.com/2.0/?method=artist.search&artist=believe&api_key=133a07d3689b4d2635303f5c8332b60e&format=json

interface ArtistApi {
    @GET("2.0")
    fun searchArtist(
        @Query("method") method: String,
        @Query("artist") artist: String,
        @Query("api_key") apiKey: String,
        @Query("format") format: String
    ): Call<ArtistSearchApiResponse>
}