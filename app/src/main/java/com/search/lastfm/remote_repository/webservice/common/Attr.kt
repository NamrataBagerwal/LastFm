package com.search.lastfm.remote_repository.webservice.common

import com.google.gson.annotations.SerializedName

data class Attr(
    @SerializedName("for")
    val attrValue: String
)