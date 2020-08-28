package com.search.lastfm.remote_repository.webservice.common

import com.fasterxml.jackson.annotation.JsonProperty

data class Image(
    @JsonProperty("#text")
    val text: String,
    val size: String
)