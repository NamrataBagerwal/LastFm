package com.search.lastfm.remote_repository.webservice.album

import com.search.lastfm.remote_repository.webservice.album.entity.AlbumSearchApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

//http://ws.audioscrobbler.com/2.0/?method=album.search&album=believe&api_key=133a07d3689b4d2635303f5c8332b60e&format=json

interface AlbumApi {

    @GET("2.0/")
    fun searchAlbum(
        @Query("method") method: String,
        @Query("album") album: String,
        @Query("api_key") apiKey: String,
        @Query("format") format: String
    ): Call<AlbumSearchApiResponse>
}


