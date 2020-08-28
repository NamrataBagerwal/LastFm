package com.search.lastfm.remote_repository.webservice.artist

import com.search.lastfm.AppConstants
import com.search.lastfm.remote_repository.networking_retrofit.RetrofitFactory

object ArtistApiFactory {
    val artistApi: ArtistApi = RetrofitFactory.retrofit(AppConstants.BASE_URL)
        .create(ArtistApi::class.java)
}