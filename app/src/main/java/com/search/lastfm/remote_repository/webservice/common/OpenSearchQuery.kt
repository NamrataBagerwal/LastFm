package com.search.lastfm.remote_repository.webservice.common

import com.fasterxml.jackson.annotation.JsonProperty

data class OpenSearchQuery(
    @JsonProperty("#text")
    val text: String,
    val role: String,
    val startPage: String
)