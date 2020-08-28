package com.search.lastfm.remote_repository.webservice.song

import com.search.lastfm.AppConstants
import com.search.lastfm.remote_repository.networking_retrofit.RetrofitFactory

object SongApiFactory {
    val songApi: SongApi = RetrofitFactory.retrofit(AppConstants.BASE_URL)
        .create(SongApi::class.java)
}