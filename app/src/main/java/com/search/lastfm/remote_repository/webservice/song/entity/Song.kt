package com.search.lastfm.remote_repository.webservice.song.entity

import com.search.lastfm.remote_repository.webservice.common.Image

data class Song(
    val artist: String,
    val image: List<Image>,
    val listeners: String,
    val mbid: String,
    val name: String,
    val streamable: String,
    val url: String
)