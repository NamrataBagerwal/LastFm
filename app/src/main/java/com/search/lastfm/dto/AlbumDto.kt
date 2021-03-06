package com.search.lastfm.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AlbumDto (
    val artist: String?,
    val image: String?,
    val mbid: String?,
    val name: String?,
    val streamable: String?,
    val url: String?
): Parcelable