package com.search.lastfm.ui.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.Menu
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
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
import com.search.lastfm.util.NetworkUtility
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
    private val errorTextView: TextView by lazy { findViewById(R.id.errorTextView) }
    private val linearLayoutSearchResults: LinearLayout by lazy { findViewById(R.id.linearLayoutSearchResults) }

    private lateinit var searchView: SearchView

    private companion object {
        private const val CURRENT_VIEW = "CURRENT_VIEW"
        private const val HOME_PAGE = "HOME_PAGE"
        private const val SEARCH_RESULT_VIEW = "SEARCH_RESULT_VIEW"
        private const val ERROR_VIEW = "ERROR_VIEW"
        private const val ERROR_VIEW_MESSAGE = "ERROR_VIEW_MESSAGE"
        private const val CURRENT_SEARCH_QUERY = "CURRENT_SEARCH_QUERY"
    }

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

        if (savedInstanceState == null) {
            showHomePage()
            handleIntent(intent)
        } else
            handleSavedInstanceState(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        // Associate searchable configuration with the SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.app_bar_search).actionView as SearchView
        searchView.apply {
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

            if (NetworkUtility.isNetworkAvailable(this@MainActivity) == null) {
                showErrorView(getString(R.string.network_unavailable_error_msg))
                return
            }

            showProgressBar()

            observeLiveData()

            setSeeAllItemClickListeners()

            val query = intent.getStringExtra(SearchManager.QUERY)
            query?.let {
                viewModel.updateAlbumLiveData(it)
                viewModel.updateArtistLiveData(it)
                viewModel.updateSongLiveData(it)
            }

        }
    }

    private fun observeLiveData() {

        observeAlbumLiveData()

        observeArtistLiveData()

        observeSongLiveData()

    }

    private fun observeAlbumLiveData() {
        viewModel.getAlbumLiveData().observe(this@MainActivity, { albumList ->

            if (albumList.isNullOrEmpty()) {
                showErrorView(getString(R.string.server_unavailable_error_msg))
            } else {
                showSearchResultsView()

                albumAdapter = AlbumAdapter(onAlbumClickListener = { albumDto: AlbumDto ->

                    val intent = Intent(this@MainActivity, SearchResultDetailActivity::class.java)
                    intent.putExtra(
                        AppConstants.TYPE_OF_ITEM,
                        getString(R.string.all_albums_tv_label)
                    )
                    intent.putExtra(getString(R.string.all_albums_tv_label), albumDto)
                    startActivity(intent)

                })
                popularAlbumsRecyclerView.adapter = albumAdapter
                albumAdapter.albumList = albumList
            }
        })
    }

    private fun observeArtistLiveData() {
        viewModel.getArtistLiveData().observe(this@MainActivity, { artistList ->
            if (artistList.isNullOrEmpty()) {
                showErrorView(getString(R.string.server_unavailable_error_msg))
            } else {
                showSearchResultsView()

                artistAdapter = ArtistAdapter(onArtistClickListener = { artistDto: ArtistDto ->

                    val intent = Intent(this@MainActivity, SearchResultDetailActivity::class.java)
                    intent.putExtra(
                        AppConstants.TYPE_OF_ITEM,
                        getString(R.string.all_artists_tv_label)
                    )
                    intent.putExtra(getString(R.string.all_artists_tv_label), artistDto)
                    startActivity(intent)

                })
                featuredArtistsRecyclerView.adapter = artistAdapter
                artistAdapter.artistList = artistList
            }
        })
    }

    private fun observeSongLiveData() {
        viewModel.getSongLiveData().observe(this@MainActivity, { songList ->
            if (songList.isNullOrEmpty()) {
                showErrorView(getString(R.string.server_unavailable_error_msg))
            } else {
                showSearchResultsView()

                songAdapter = SongAdapter(onSongClickListener = { songDto: SongDto ->

                    val intent = Intent(this@MainActivity, SearchResultDetailActivity::class.java)
                    intent.putExtra(
                        AppConstants.TYPE_OF_ITEM,
                        getString(R.string.all_songs_tv_label)
                    )
                    intent.putExtra(getString(R.string.all_songs_tv_label), songDto)
                    startActivity(intent)

                })
                trendingSongsRecyclerView.adapter = songAdapter
                songAdapter.songList = songList
            }
        })
    }

    private fun setSeeAllItemClickListeners() {
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

    override fun onSaveInstanceState(outState: Bundle) {
        when {
            imageViewHomePage.visibility == View.VISIBLE -> outState.putString(
                CURRENT_VIEW,
                HOME_PAGE
            )
            linearLayoutSearchResults.visibility == View.VISIBLE -> {
                outState.putString(
                    CURRENT_VIEW,
                    SEARCH_RESULT_VIEW
                )
                outState.putString(CURRENT_SEARCH_QUERY, intent.getStringExtra(SearchManager.QUERY))
            }
            errorTextView.visibility == View.VISIBLE -> {
                outState.putString(CURRENT_VIEW, ERROR_VIEW)
                outState.putString(ERROR_VIEW_MESSAGE, errorTextView.text.toString())
            }
        }

        super.onSaveInstanceState(outState)
    }

    private fun showSearchResultsView() {
        hideProgressBar()
        errorTextView.visibility = View.GONE
        linearLayoutSearchResults.visibility = View.VISIBLE
        imageViewHomePage.visibility = View.GONE
    }

    private fun showHomePage() {
        errorTextView.visibility = View.GONE
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
        if (imageViewHomePage.visibility != View.VISIBLE) {
            showHomePage()
            searchView.onActionViewCollapsed()
        } else {
            super.onBackPressed()
        }
    }

    private fun showErrorView(errorMessage: String) {
        hideProgressBar()
        errorTextView.visibility = View.VISIBLE
        linearLayoutSearchResults.visibility = View.GONE
        imageViewHomePage.visibility = View.GONE
        errorTextView.text = errorMessage
    }

    private fun handleSavedInstanceState(outState: Bundle) {
        when (outState.getString(CURRENT_VIEW)) {
            HOME_PAGE -> showHomePage()
            SEARCH_RESULT_VIEW -> {
                // Just set the search query in intent and let ViewModel handle painting the data on UI

                val intent = Intent()
                intent.action = Intent.ACTION_SEARCH
                intent.putExtra(SearchManager.QUERY, outState.getString(CURRENT_SEARCH_QUERY))
                handleIntent(intent)
            }
            ERROR_VIEW -> showErrorView(outState.getString(ERROR_VIEW_MESSAGE)!!)
        }
    }
}