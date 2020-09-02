package com.search.lastfm.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.search.lastfm.R
import com.search.lastfm.dto.SongDto

class SongAdapter(val onSongClickListener: (songDto: SongDto) -> Unit) :
    RecyclerView.Adapter<SongAdapter.SongViewHolder>() {

    var songList: List<SongDto> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_list_item, parent, false)
        return SongViewHolder(view)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val songDto: SongDto = songList[position]

        Glide.with(holder.itemView.context)
            .load(songDto.image)
            .placeholder(R.drawable.placeholder_image)
            .fallback(R.drawable.placeholder_image)
            .into(holder.imageView)

        holder.title.text = songDto.name

        holder.description.text = songDto.artist

        holder.itemView.setOnClickListener {
            onSongClickListener(songDto)
        }
    }

    override fun getItemCount() = songList.size

    class SongViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.itemTitleTextView)
        val description: TextView = itemView.findViewById(R.id.itemDescriptionTextView)
        val imageView: ImageView = itemView.findViewById(R.id.thumbNailImageView)
    }
}