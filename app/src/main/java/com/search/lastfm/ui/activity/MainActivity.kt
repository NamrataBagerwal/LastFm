package com.search.lastfm.ui.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.Menu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
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
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModel()

    private val trendingSongsRecyclerView: RecyclerView by lazy { findViewById(R.id.trendingSongsRecyclerView) }
    private val popularAlbumsRecyclerView: RecyclerView by lazy { findViewById(R.id.popularAlbumsRecyclerView) }
    private val featuredArtistsRecyclerView: RecyclerView by lazy { findViewById(R.id.featuredArtistsRecyclerView) }

    private val allSongsTextView: TextView by lazy { findViewById(R.id.allSongsTextView) }
    private val allAlbumsTextView: TextView by lazy { findViewById(R.id.allAlbumsTextView) }
    private val allArtistsTextView: TextView by lazy { findViewById(R.id.allArtistsTextView) }

    private lateinit var songAdapter: SongAdapter
    private lateinit var albumAdapter: AlbumAdapter
    private lateinit var artistAdapter: ArtistAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        handleIntent(intent)

        observeLiveData()

        setOnClickListeners()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
//
//        val searchItem: MenuItem = menu.findItem(R.id.app_bar_search)
//        val searchView = searchItem.actionView as SearchView
//
//        val searchPlate =  searchView.findViewById(androidx.appcompat.R.id.search_src_text) as EditText
//        searchPlate.hint = "Search"
//
//        val searchPlateView: View =
//            searchView.findViewById(androidx.appcompat.R.id.search_plate)
//        searchPlateView.setBackgroundColor(
//            ContextCompat.getColor(
//                this,
//                android.R.color.transparent
//            )
//        )
//
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//// do your logic here
// Toast.makeText(applicationContext, query, Toast.LENGTH_SHORT).show()
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                return false
//            }
//        })
//
//        val searchManager =
//            getSystemService(Context.SEARCH_SERVICE) as SearchManager
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
//        return super.onCreateOptionsMenu(menu)

        // Associate searchable configuration with the SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.app_bar_search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            Log.i("TAG", "SUBMIT BUTTON PRESS")
        }
        return true
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {

        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            Toast.makeText(applicationContext, query, Toast.LENGTH_SHORT).show()

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
            Log.i("TAG", albumList.size.toString())
            Toast.makeText(applicationContext, albumList.size.toString(), Toast.LENGTH_SHORT).show()

            albumAdapter = AlbumAdapter(onAlbumClickListener = { albumDto: AlbumDto ->
                Toast.makeText(applicationContext, albumDto.artist.toString(), Toast.LENGTH_SHORT)
                    .show()
            })
            popularAlbumsRecyclerView.adapter = albumAdapter
            albumAdapter.albumList = albumList
        })

        viewModel.getArtistLiveData().observe(this@MainActivity, { artistList ->
            Log.i("TAG", artistList.size.toString())
            Toast.makeText(applicationContext, artistList.size.toString(), Toast.LENGTH_SHORT)
                .show()

            artistAdapter = ArtistAdapter(onArtistClickListener = { artistDto: ArtistDto ->
                Toast.makeText(applicationContext, artistDto.name.toString(), Toast.LENGTH_SHORT)
                    .show()
            })
            featuredArtistsRecyclerView.adapter = artistAdapter
            artistAdapter.artistList = artistList

        })

        viewModel.getSongLiveData().observe(this@MainActivity, { songList ->
            Log.i("TAG", songList.size.toString())
            Toast.makeText(applicationContext, songList.size.toString(), Toast.LENGTH_SHORT).show()

            songAdapter = SongAdapter(onSongClickListener = { songDto: SongDto ->
                Toast.makeText(applicationContext, songDto.name.toString(), Toast.LENGTH_SHORT)
                    .show()
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
            this@MainActivity.startActivity(intent)
        }
        allAlbumsTextView.setOnClickListener {
            bundle.putString(AppConstants.TYPE_OF_ITEM, getString(R.string.all_albums_tv_label))
            bundle.putParcelableArrayList(
                getString(R.string.all_albums_tv_label),
                albumAdapter.albumList as ArrayList<out Parcelable>
            )
            intent.putExtras(bundle)
            this@MainActivity.startActivity(intent)
        }
        allArtistsTextView.setOnClickListener {
            bundle.putString(AppConstants.TYPE_OF_ITEM, getString(R.string.all_artists_tv_label))
            bundle.putParcelableArrayList(
                getString(R.string.all_artists_tv_label),
                artistAdapter.artistList as ArrayList<out Parcelable>
            )
            intent.putExtras(bundle)
            this@MainActivity.startActivity(intent)
        }
    }
}