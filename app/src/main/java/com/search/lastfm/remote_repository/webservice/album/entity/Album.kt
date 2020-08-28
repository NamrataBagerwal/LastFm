package com.search.lastfm.remote_repository.webservice.album.entity

import com.search.lastfm.remote_repository.webservice.common.Image

data class Album(
    val artist: String,
    val image: List<Image>,
    val mbid: String,
    val name: String,
    val streamable: String,
    val url: String
)