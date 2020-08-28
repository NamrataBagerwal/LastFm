package com.search.lastfm.remote_repository.webservice.album

import com.search.lastfm.AppConstants
import com.search.lastfm.remote_repository.networking_retrofit.RetrofitFactory

object AlbumApiFactory {
    val albumApi: AlbumApi = RetrofitFactory.retrofit(AppConstants.BASE_URL)
        .create(AlbumApi::class.java)
}