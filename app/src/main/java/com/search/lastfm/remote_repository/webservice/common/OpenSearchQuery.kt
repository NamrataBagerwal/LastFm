package com.search.lastfm.remote_repository.webservice.common

import com.google.gson.annotations.SerializedName

data class OpenSearchQuery(
    @SerializedName("#text")
    val text: String,
    val role: String,
    val startPage: String
)