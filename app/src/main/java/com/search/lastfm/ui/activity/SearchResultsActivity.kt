package com.search.lastfm.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.search.lastfm.AppConstants
import com.search.lastfm.R
import com.search.lastfm.dto.AlbumDto
import com.search.lastfm.dto.ArtistDto
import com.search.lastfm.dto.SongDto
import com.search.lastfm.ui.adapter.AlbumAdapter
import com.search.lastfm.ui.adapter.ArtistAdapter
import com.search.lastfm.ui.adapter.SongAdapter

class SearchResultsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showSearchResultsView()

        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {

        val typeOfItem = intent.getStringExtra(AppConstants.TYPE_OF_ITEM)
        when (typeOfItem) {
            getString(R.string.all_songs_tv_label) -> {
                setSongResults(typeOfItem)
            }

            getString(R.string.all_albums_tv_label) -> {
                setAlbumResults(typeOfItem)
            }

            getString(R.string.all_artists_tv_label) -> {
                setArtistResults(typeOfItem)
            }
        }

        showRelevantView(typeOfItem)
    }

    private fun showRelevantView(typeOfItem: String?) {
        when (typeOfItem) {

            getString(R.string.all_songs_tv_label) -> {

                findViewById<TextView>(R.id.allSongsTextView).visibility = View.GONE

                findViewById<TextView>(R.id.popularAlbumsTextView).visibility = View.GONE
                findViewById<RecyclerView>(R.id.popularAlbumsRecyclerView).visibility = View.GONE
                findViewById<TextView>(R.id.allAlbumsTextView).visibility = View.GONE

                findViewById<TextView>(R.id.featuredArtistsTextView).visibility = View.GONE
                findViewById<RecyclerView>(R.id.featuredArtistsRecyclerView).visibility = View.GONE
                findViewById<TextView>(R.id.allArtistsTextView).visibility = View.GONE
            }

            getString(R.string.all_albums_tv_label) -> {

                findViewById<TextView>(R.id.allAlbumsTextView).visibility = View.GONE

                findViewById<TextView>(R.id.trendingSongsTextView).visibility = View.GONE
                findViewById<RecyclerView>(R.id.trendingSongsRecyclerView).visibility = View.GONE
                findViewById<TextView>(R.id.allSongsTextView).visibility = View.GONE

                findViewById<TextView>(R.id.featuredArtistsTextView).visibility = View.GONE
                findViewById<RecyclerView>(R.id.featuredArtistsRecyclerView).visibility = View.GONE
                findViewById<TextView>(R.id.allArtistsTextView).visibility = View.GONE
            }

            getString(R.string.all_artists_tv_label) -> {

                findViewById<TextView>(R.id.allArtistsTextView).visibility = View.GONE

                findViewById<TextView>(R.id.trendingSongsTextView).visibility = View.GONE
                findViewById<RecyclerView>(R.id.trendingSongsRecyclerView).visibility = View.GONE
                findViewById<TextView>(R.id.allSongsTextView).visibility = View.GONE

                findViewById<TextView>(R.id.popularAlbumsTextView).visibility = View.GONE
                findViewById<RecyclerView>(R.id.popularAlbumsRecyclerView).visibility = View.GONE
                findViewById<TextView>(R.id.allAlbumsTextView).visibility = View.GONE
            }
        }
    }

    private fun showSearchResultsView() {
        findViewById<LinearLayout>(R.id.linearLayoutSearchResults).visibility = View.VISIBLE
        findViewById<ImageView>(R.id.imageViewHomePage).visibility = View.GONE
        findViewById<TextView>(R.id.errorTextView).visibility = View.GONE
    }

    private fun setSongResults(typeOfItem: String) {

        val songList = intent.getParcelableArrayListExtra<SongDto>(typeOfItem)

        val trendingSongsRecyclerView: RecyclerView =
            findViewById(R.id.trendingSongsRecyclerView)
        val layoutParams = trendingSongsRecyclerView.layoutParams
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
        trendingSongsRecyclerView.layoutParams = layoutParams

        val songAdapter = SongAdapter { songDto: SongDto ->

            val intent =
                Intent(this@SearchResultsActivity, SearchResultDetailActivity::class.java)
            intent.putExtra(AppConstants.TYPE_OF_ITEM, typeOfItem)
            intent.putExtra(typeOfItem, songDto)
            startActivity(intent)
        }
        trendingSongsRecyclerView.adapter = songAdapter
        songAdapter.songList = songList as List<SongDto>
    }

    private fun setAlbumResults(typeOfItem: String) {
        val albumList = intent.getParcelableArrayListExtra<AlbumDto>(typeOfItem)

        val popularAlbumsRecyclerView: RecyclerView =
            findViewById(R.id.popularAlbumsRecyclerView)
        val layoutParams = popularAlbumsRecyclerView.layoutParams
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
        popularAlbumsRecyclerView.layoutParams = layoutParams

        val albumAdapter = AlbumAdapter(onAlbumClickListener = { albumDto: AlbumDto ->

            val intent =
                Intent(this@SearchResultsActivity, SearchResultDetailActivity::class.java)
            intent.putExtra(AppConstants.TYPE_OF_ITEM, typeOfItem)
            intent.putExtra(typeOfItem, albumDto)
            startActivity(intent)
        })
        popularAlbumsRecyclerView.adapter = albumAdapter
        albumAdapter.albumList = albumList as List<AlbumDto>
    }

    private fun setArtistResults(typeOfItem: String) {
        val artistList = intent.getParcelableArrayListExtra<ArtistDto>(typeOfItem)

        val featuredArtistsRecyclerView: RecyclerView =
            findViewById(R.id.featuredArtistsRecyclerView)
        val layoutParams = featuredArtistsRecyclerView.layoutParams
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
        featuredArtistsRecyclerView.layoutParams = layoutParams

        val artistAdapter = ArtistAdapter(onArtistClickListener = { artistDto: ArtistDto ->

            val intent =
                Intent(this@SearchResultsActivity, SearchResultDetailActivity::class.java)
            intent.putExtra(AppConstants.TYPE_OF_ITEM, typeOfItem)
            intent.putExtra(typeOfItem, artistDto)
            startActivity(intent)
        })
        featuredArtistsRecyclerView.adapter = artistAdapter
        artistAdapter.artistList = artistList as List<ArtistDto>
    }
}