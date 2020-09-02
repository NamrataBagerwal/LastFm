package com.search.lastfm.ui.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.widget.ContentLoadingProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.search.lastfm.AppConstants
import com.search.lastfm.R
import com.search.lastfm.dto.AlbumDto
import com.search.lastfm.dto.ArtistDto
import com.search.lastfm.dto.SongDto
import com.search.lastfm.ui.adapter.AlbumAdapter
import com.search.lastfm.ui.adapter.ArtistAdapter
import com.search.lastfm.ui.adapter.SongAdapter
import com.search.lastfm.ui.viewmodel.MainActivityViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModel()

    private val trendingSongsRecyclerView: RecyclerView by lazy { findViewById(R.id.trendingSongsRecyclerView) }
    private val popularAlbumsRecyclerView: RecyclerView by lazy { findViewById(R.id.popularAlbumsRecyclerView) }
    private val featuredArtistsRecyclerView: RecyclerView by lazy { findViewById(R.id.featuredArtistsRecyclerView) }

    private val allSongsTextView: TextView by lazy { findViewById(R.id.allSongsTextView) }
    private val allAlbumsTextView: TextView by lazy { findViewById(R.id.allAlbumsTextView) }
    private val allArtistsTextView: TextView by lazy { findViewById(R.id.allArtistsTextView) }

    private val imageViewHomePage: ImageView by lazy { findViewById(R.id.imageViewHomePage) }
    private val linearLayoutSearchResults: LinearLayout by lazy { findViewById(R.id.linearLayoutSearchResults) }
    private val progressBar: ContentLoadingProgressBar by lazy {
        findViewById(
            R.id.contentLoadingProgressBar
        )
    }

    private lateinit var songAdapter: SongAdapter
    private lateinit var albumAdapter: AlbumAdapter
    private lateinit var artistAdapter: ArtistAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        println("TAG onCreate" )

        showHomePage()

        handleIntent(intent)

        observeLiveData()

        setOnClickListeners()
    }

    override fun onResume() {
        super.onResume()
        println("TAG onResume" )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)

        println("TAG onCreateOptionsMenu" )

        println("TAG onCreateOptionsMenu ${menu.findItem(R.id.app_bar_search)}" )
        // Associate searchable configuration with the SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.app_bar_search).actionView as SearchView).apply {
            setOnCloseListener {
                showHomePage()
                false
            }
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }
        return true
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {

        if (Intent.ACTION_SEARCH == intent.action) {

            showProgressBar()

            val query = intent.getStringExtra(SearchManager.QUERY)

            //use the query to search your data somehow
            query?.let {
                viewModel.updateAlbumLiveData(it)
                viewModel.updateArtistLiveData(it)
                viewModel.updateSongLiveData(it)
            }

        }
    }

    private fun observeLiveData() {
        viewModel.getAlbumLiveData().observe(this@MainActivity, { albumList ->

            showSearchResultsView()

            albumAdapter = AlbumAdapter(onAlbumClickListener = { albumDto: AlbumDto ->

                val intent = Intent(this@MainActivity, SearchResultDetailActivity::class.java)
                intent.putExtra(AppConstants.TYPE_OF_ITEM, getString(R.string.all_albums_tv_label))
                intent.putExtra(getString(R.string.all_albums_tv_label), albumDto)
                startActivity(intent)

            })
            popularAlbumsRecyclerView.adapter = albumAdapter
            albumAdapter.albumList = albumList
        })

        viewModel.getArtistLiveData().observe(this@MainActivity, { artistList ->

            showSearchResultsView()

            artistAdapter = ArtistAdapter(onArtistClickListener = { artistDto: ArtistDto ->

                val intent = Intent(this@MainActivity, SearchResultDetailActivity::class.java)
                intent.putExtra(AppConstants.TYPE_OF_ITEM, getString(R.string.all_artists_tv_label))
                intent.putExtra(getString(R.string.all_artists_tv_label), artistDto)
                startActivity(intent)

            })
            featuredArtistsRecyclerView.adapter = artistAdapter
            artistAdapter.artistList = artistList

        })

        viewModel.getSongLiveData().observe(this@MainActivity, { songList ->

            showSearchResultsView()

            songAdapter = SongAdapter(onSongClickListener = { songDto: SongDto ->

                val intent = Intent(this@MainActivity, SearchResultDetailActivity::class.java)
                intent.putExtra(AppConstants.TYPE_OF_ITEM, getString(R.string.all_songs_tv_label))
                intent.putExtra(getString(R.string.all_songs_tv_label), songDto)
                startActivity(intent)

            })
            trendingSongsRecyclerView.adapter = songAdapter
            songAdapter.songList = songList
        })
    }

    private fun setOnClickListeners() {
        val intent = Intent(this@MainActivity, SearchResultsActivity::class.java)
        val bundle = Bundle()

        allSongsTextView.setOnClickListener {
            bundle.putString(AppConstants.TYPE_OF_ITEM, getString(R.string.all_songs_tv_label))
            bundle.putParcelableArrayList(
                getString(R.string.all_songs_tv_label),
                songAdapter.songList as ArrayList<out Parcelable>
            )
            intent.putExtras(bundle)
            startActivity(intent)
        }
        allAlbumsTextView.setOnClickListener {
            bundle.putString(AppConstants.TYPE_OF_ITEM, getString(R.string.all_albums_tv_label))
            bundle.putParcelableArrayList(
                getString(R.string.all_albums_tv_label),
                albumAdapter.albumList as ArrayList<out Parcelable>
            )
            intent.putExtras(bundle)
            startActivity(intent)
        }
        allArtistsTextView.setOnClickListener {
            bundle.putString(AppConstants.TYPE_OF_ITEM, getString(R.string.all_artists_tv_label))
            bundle.putParcelableArrayList(
                getString(R.string.all_artists_tv_label),
                artistAdapter.artistList as ArrayList<out Parcelable>
            )
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }

    private fun showSearchResultsView(){
        hideProgressBar()
        linearLayoutSearchResults.visibility = View.VISIBLE
        imageViewHomePage.visibility = View.GONE
    }

    private fun showHomePage(){
        linearLayoutSearchResults.visibility = View.GONE
        imageViewHomePage.visibility = View.VISIBLE
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
        progressBar.show()
    }

    private fun hideProgressBar() {
        progressBar.hide()
        progressBar.visibility = View.GONE

    }

    override fun onBackPressed() {
        if(linearLayoutSearchResults.visibility == View.VISIBLE)
            showHomePage()
        else
            super.onBackPressed()
    }
}