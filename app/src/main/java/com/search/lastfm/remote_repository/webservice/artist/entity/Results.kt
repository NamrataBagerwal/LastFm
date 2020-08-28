package com.search.lastfm.remote_repository.webservice.artist.entity

import com.fasterxml.jackson.annotation.JsonProperty
import com.search.lastfm.remote_repository.webservice.common.Attr
import com.search.lastfm.remote_repository.webservice.common.OpenSearchQuery

data class Results(
    @JsonProperty("@attr")
    val attr: Attr,
    val artistmatches: Artistmatches,
    @JsonProperty("opensearch:Query")
    val openSearchQuery: OpenSearchQuery,
    @JsonProperty("opensearch:itemsPerPage")
    val openSearchItemsPerPage: String,
    @JsonProperty("opensearch:startIndex")
    val openSearchStartIndex: String,
    @JsonProperty("opensearch:totalResults")
    val openSearchTotalResults: String
)