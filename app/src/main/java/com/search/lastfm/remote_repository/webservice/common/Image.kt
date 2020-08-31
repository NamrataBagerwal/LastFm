package com.search.lastfm.remote_repository.webservice.common

import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("#text")
    val text: String,
    val size: String
)