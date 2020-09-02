package com.search.lastfm.ui.util

import com.search.lastfm.dto.AlbumDto
import com.search.lastfm.dto.ArtistDto
import com.search.lastfm.dto.SongDto
import com.search.lastfm.remote_repository.webservice.album.entity.Album
import com.search.lastfm.remote_repository.webservice.album.entity.AlbumSearchApiResponse
import com.search.lastfm.remote_repository.webservice.album.entity.Albummatches
import com.search.lastfm.remote_repository.webservice.album.entity.Results
import com.search.lastfm.remote_repository.webservice.artist.entity.Artist
import com.search.lastfm.remote_repository.webservice.artist.entity.ArtistSearchApiResponse
import com.search.lastfm.remote_repository.webservice.artist.entity.Artistmatches
import com.search.lastfm.remote_repository.webservice.common.Attr
import com.search.lastfm.remote_repository.webservice.common.Image
import com.search.lastfm.remote_repository.webservice.common.OpenSearchQuery
import com.search.lastfm.remote_repository.webservice.song.entity.Song
import com.search.lastfm.remote_repository.webservice.song.entity.SongSearchApiResponse
import com.search.lastfm.remote_repository.webservice.song.entity.TrackMatches

object TestData {

    private const val ARTIST1 = "ARTIST1"
    private const val ARTIST2 = "ARTIST2"
    private const val IMAGE1 = "IMAGE1"
    private const val IMAGE2 = "IMAGE2"
    private const val LISTENERS1 = "LISTENERS1"
    private const val LISTENERS2 = "LISTENERS2"
    private const val MBID1 = "MBID1"
    private const val MBID2 = "MBID2"
    private const val NAME1 = "NAME1"
    private const val NAME2 = "NAME2"
    private const val STREAMABLE1 = "STREAMABLE1"
    private const val STREAMABLE2 = "STREAMABLE2"
    private const val URL1 = "URL1"
    private const val URL2 = "URL2"
    private const val OPENSEARCHITEMSPERPAGE = "OPENSEARCHITEMSPERPAGE"
    private const val OPENSEARCHSTARTINDEX = "OPENSEARCHSTARTINDEX"
    private const val OPENSEARCHTOTALRESULTS = "OPENSEARCHTOTALRESULTS"
    private const val TEXT1 = "TEXT1"
    private const val TEXT2 = "TEXT2"
    private const val SIZE1 = "SIZE1"
    private const val SIZE2 = "SIZE2"
    private const val TEXT = "TEXT"
    private const val ROLE = "ROLE"
    private const val STARTPAGE = "STARTPAGE"
    private const val ATTR = "ATTR"
    const val SEARCH_QUERY = "name"
    const val API_KEY = "API_KEY"
    const val JSON_FORMAT = "JSON_FORMAT"

    fun getSongListTestResponse(): List<SongDto> {
        return mutableListOf<SongDto>(
            SongDto(ARTIST1, IMAGE1, LISTENERS1, MBID1, NAME1, STREAMABLE1, URL1),
            SongDto(ARTIST1, IMAGE1, LISTENERS2, MBID2, NAME2, STREAMABLE2, URL2),
        )
    }

    fun getAlbumListTestResponse(): List<AlbumDto> {
        return mutableListOf<AlbumDto>(
            AlbumDto(ARTIST1, IMAGE1, MBID1, NAME1, STREAMABLE1, URL1),
            AlbumDto(ARTIST2, IMAGE2, MBID2, NAME2, STREAMABLE2, URL2),
        )
    }

    fun getArtistListTestResponse(): List<ArtistDto> {
        return mutableListOf<ArtistDto>(
            ArtistDto(IMAGE1, LISTENERS1, MBID1, NAME1, STREAMABLE1, URL1),
            ArtistDto(IMAGE2, LISTENERS2, MBID2, NAME2, STREAMABLE2, URL2),
        )
    }

    fun getAlbumApiTestResponse(): AlbumSearchApiResponse {

        val album1 = Album(ARTIST1, getIMAGE1(), MBID1, NAME1, STREAMABLE1, URL1)
        val album2 = Album(ARTIST2, getIMAGE2(), MBID2, NAME2, STREAMABLE2, URL2)

        val albumMatches = Albummatches(listOf<Album>(album1, album2))

        val results = Results(
            getAttr(),
            albumMatches,
            getOpenSearchQuery(),
            OPENSEARCHITEMSPERPAGE,
            OPENSEARCHSTARTINDEX,
            OPENSEARCHTOTALRESULTS
        )

        return AlbumSearchApiResponse(results)
    }

    fun getArtistApiTestResponse(): ArtistSearchApiResponse {

        val artist1 = Artist(getIMAGE1(), LISTENERS1, MBID1, NAME1, STREAMABLE1, URL1)
        val artist2 = Artist(getIMAGE2(), LISTENERS2, MBID2, NAME2, STREAMABLE2, URL2)

        val artistMatches = Artistmatches(listOf<Artist>(artist1, artist2))

        val results = com.search.lastfm.remote_repository.webservice.artist.entity.Results(
            getAttr(),
            artistMatches,
            getOpenSearchQuery(),
            OPENSEARCHITEMSPERPAGE,
            OPENSEARCHSTARTINDEX,
            OPENSEARCHTOTALRESULTS
        )

        return ArtistSearchApiResponse(results)
    }

    fun getSongApiTestResponse(): SongSearchApiResponse {

        val song1 = Song(ARTIST1, getIMAGE1(), LISTENERS1, MBID1, NAME1, STREAMABLE1, URL1)
        val song2 = Song(ARTIST2, getIMAGE2(), LISTENERS2, MBID2, NAME2, STREAMABLE2, URL2)

        val songMatches = TrackMatches(listOf<Song>(song1, song2))

        val results = com.search.lastfm.remote_repository.webservice.song.entity.Results(
            getAttr(),
            getOpenSearchQuery(),
            OPENSEARCHITEMSPERPAGE,
            OPENSEARCHSTARTINDEX,
            OPENSEARCHTOTALRESULTS,
            songMatches
        )

        return SongSearchApiResponse(results)
    }

    private fun getIMAGE1(): List<Image> {
        return listOf<Image>(
            Image(TEXT1, SIZE1),
            Image(TEXT2, SIZE2)
        )
    }

    private fun getIMAGE2(): List<Image> {
        return listOf<Image>(
            Image(TEXT1, SIZE1),
            Image(TEXT2, SIZE2)
        )
    }

    private fun getOpenSearchQuery(): OpenSearchQuery {
        return OpenSearchQuery(TEXT, ROLE, STARTPAGE)
    }

    private fun getAttr(): Attr {
        return Attr(ATTR)
    }

}