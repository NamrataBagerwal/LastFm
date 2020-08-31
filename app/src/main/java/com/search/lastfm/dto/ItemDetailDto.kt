package com.search.lastfm.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItemDetailDto (
     val artist: String?,
     val image: String?,
     val listeners: String?,
     val mbid: String?,
     val name: String?,
     val streamable: String?,
     val url: String?
): Parcelable