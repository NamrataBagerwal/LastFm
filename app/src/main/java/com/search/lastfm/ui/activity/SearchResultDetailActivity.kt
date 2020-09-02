package com.search.lastfm.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.search.lastfm.AppConstants
import com.search.lastfm.R
import com.search.lastfm.dto.AlbumDto
import com.search.lastfm.dto.ArtistDto
import com.search.lastfm.dto.SongDto

class SearchResultDetailActivity : AppCompatActivity() {
    private val thumbNailImageView: ImageView by lazy { findViewById(R.id.thumbNailImageView) }
    private val itemTitleTextView: TextView by lazy { findViewById(R.id.itemTitleTextView) }
    private val itemDetailTextView1: TextView by lazy { findViewById(R.id.itemDetailTextView1) }
    private val itemDetailTextView2: TextView by lazy { findViewById(R.id.itemDetailTextView2) }
    private val itemDetailTextView3: TextView by lazy { findViewById(R.id.itemDetailTextView3) }
    private val itemDetailTextView4: TextView by lazy { findViewById(R.id.itemDetailTextView4) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result_detail)

        when(val typeOfItem = intent.getStringExtra(AppConstants.TYPE_OF_ITEM)){
            getString(R.string.all_songs_tv_label) -> {

                val songDto = intent.getParcelableExtra<SongDto>(typeOfItem)
                Glide.with(this@SearchResultDetailActivity)
                    .load(songDto?.image)
                    .placeholder(R.drawable.placeholder_image)
                    .fallback(R.drawable.placeholder_image)
                    .into(thumbNailImageView)

                itemTitleTextView.text = songDto?.name
                itemDetailTextView1.text = songDto?.artist
                itemDetailTextView2.append(songDto?.listeners)
                itemDetailTextView3.append(songDto?.streamable)
                itemDetailTextView4.append(songDto?.url)

            }

            getString(R.string.all_albums_tv_label) -> {

                val albumDto = intent.getParcelableExtra<AlbumDto>(typeOfItem)
                Glide.with(this@SearchResultDetailActivity)
                    .load(albumDto?.image)
                    .placeholder(R.drawable.placeholder_image)
                    .fallback(R.drawable.placeholder_image)
                    .into(thumbNailImageView)

                itemTitleTextView.text = albumDto?.name
                itemDetailTextView1.text = albumDto?.artist
                itemDetailTextView2.visibility = View.GONE
                itemDetailTextView3.append(albumDto?.streamable)
                itemDetailTextView4.append(albumDto?.url)

            }

            getString(R.string.all_artists_tv_label) -> {

                val artistDto = intent.getParcelableExtra<ArtistDto>(typeOfItem)
                Glide.with(this@SearchResultDetailActivity)
                    .load(artistDto?.image)
                    .placeholder(R.drawable.placeholder_image)
                    .fallback(R.drawable.placeholder_image)
                    .into(thumbNailImageView)

                itemTitleTextView.text = artistDto?.name
                itemDetailTextView1.visibility = View.GONE
                itemDetailTextView2.append(artistDto?.listeners)
                itemDetailTextView3.append(artistDto?.streamable)
                itemDetailTextView4.append(artistDto?.url)

            }
        }
    }

}