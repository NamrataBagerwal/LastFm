package com.search.lastfm.remote_repository.webservice.artist.entity

import com.search.lastfm.remote_repository.webservice.common.Image

data class Artist(
    val image: List<Image>,
    val listeners: String,
    val mbid: String,
    val name: String,
    val streamable: String,
    val url: String
)