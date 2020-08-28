package com.search.lastfm.dto

data class SongDto (
    val artist: String,
    val image: String,
    val listeners: String,
    val mbid: String,
    val name: String,
    val streamable: String,
    val url: String
)