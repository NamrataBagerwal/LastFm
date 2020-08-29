package com.search.lastfm.ui.activity

import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import com.search.lastfm.R
import com.search.lastfm.ui.viewmodel.MainActivityViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        handleIntent(intent)

        observeLiveData()
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
        viewModel.getAlbumLiveData().observe(this@MainActivity, {
            albumList -> Log.i("TAG", albumList.size.toString())
            Toast.makeText(applicationContext, albumList.size.toString(), Toast.LENGTH_SHORT).show()
        })
        viewModel.getArtistLiveData().observe(this@MainActivity, {
            artistList -> Log.i("TAG", artistList.size.toString())
            Toast.makeText(applicationContext, artistList.size.toString(), Toast.LENGTH_SHORT).show()
        })
        viewModel.getSongLiveData().observe(this@MainActivity, {
                songList -> Log.i("TAG", songList.size.toString())
                Toast.makeText(applicationContext, songList.size.toString(), Toast.LENGTH_SHORT).show()
        })
    }
}