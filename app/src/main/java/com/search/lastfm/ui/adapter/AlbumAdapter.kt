package com.search.lastfm.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.search.lastfm.AppConstants.MAX_ITEMS_TO_BE_DISPLAYED
import com.search.lastfm.R
import com.search.lastfm.dto.AlbumDto

class AlbumAdapter(val onAlbumClickListener: (albumDto: AlbumDto) -> Unit) :
    RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {

    var albumList: List<AlbumDto> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_list_item, parent, false)
        return AlbumViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val albumDto: AlbumDto = albumList[position]

        Glide.with(holder.itemView.context)
            .load(albumDto.image)
            .placeholder(R.drawable.placeholder_image)
            .fallback(R.drawable.placeholder_image)
            .into(holder.imageView)

        holder.title.text = albumDto.name

        holder.description.text = albumDto.artist

        holder.itemView.setOnClickListener {
            onAlbumClickListener(albumDto)
        }
    }

    override fun getItemCount() = albumList.size

    class AlbumViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.itemTitleTextView)
        val description: TextView = itemView.findViewById(R.id.itemDescriptionTextView)
        val imageView: ImageView = itemView.findViewById(R.id.thumbNailImageView)
    }
}