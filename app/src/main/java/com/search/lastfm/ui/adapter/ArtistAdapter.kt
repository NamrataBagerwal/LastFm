package com.search.lastfm.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.search.lastfm.AppConstants
import com.search.lastfm.R
import com.search.lastfm.dto.ArtistDto

class ArtistAdapter(val onArtistClickListener: (artistDto: ArtistDto) -> Unit) :
    RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder>() {

    var artistList: List<ArtistDto> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_list_item, parent, false)
        return ArtistViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        val artistDto: ArtistDto = artistList[position]

        Glide.with(holder.itemView.context)
            .load(artistDto.image)
            .placeholder(R.drawable.placeholder_image)
            .fallback(R.drawable.placeholder_image)
            .into(holder.imageView)

        holder.title.text = artistDto.name

        holder.description.text = artistDto.listeners

        holder.itemView.setOnClickListener {
            onArtistClickListener(artistDto)
        }
    }

    override fun getItemCount() =
        if (artistList.size > AppConstants.MAX_ITEMS_TO_BE_DISPLAYED) AppConstants.MAX_ITEMS_TO_BE_DISPLAYED else artistList.size

    class ArtistViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.itemTitleTextView)
        val description: TextView = itemView.findViewById(R.id.itemDescriptionTextView)
        val imageView: ImageView = itemView.findViewById(R.id.thumbNailImageView)
    }
}