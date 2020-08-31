package com.search.lastfm.ui.activity

import android.app.SearchManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.search.lastfm.AppConstants
import com.search.lastfm.R
import com.search.lastfm.dto.AlbumDto

class SearchResultsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {

//        var itemList: List<>
        val typeOfItem = intent.getStringExtra(AppConstants.TYPE_OF_ITEM)
        when(typeOfItem){
            getString(R.string.all_songs_tv_label) -> {

            }
        }
//        val itemList = intent.getParcelableArrayListExtra<AlbumDto>(typeOfItem)

        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            Toast.makeText(applicationContext, query, Toast.LENGTH_SHORT).show()
            //use the query to search your data somehow
        }
    }
}