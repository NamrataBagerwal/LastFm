package com.search.lastfm.remote_repository.webservice.song.entity

import com.google.gson.annotations.SerializedName
import com.search.lastfm.remote_repository.webservice.common.Attr
import com.search.lastfm.remote_repository.webservice.common.OpenSearchQuery

data class Results(
    @SerializedName("@attr")
    val attr: Attr,
    @SerializedName("opensearch:Query")
    val openSearchQuery: OpenSearchQuery,
    @SerializedName("opensearch:itemsPerPage")
    val openSearchItemsPerPage: String,
    @SerializedName("opensearch:startIndex")
    val openSearchStartIndex: String,
    @SerializedName("opensearch:totalResults")
    val openSearchTotalResults: String,
    @SerializedName("trackmatches")
    val trackMatches: TrackMatches
)