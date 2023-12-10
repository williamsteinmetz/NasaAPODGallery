package com.steinmetz.msu.nasaphotogallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.steinmetz.msu.nasaphotogallery.api.NasaResponse
import com.steinmetz.msu.nasaphotogallery.databinding.ListItemGalleryBinding

// PhotoViewHolder: Defines the ViewHolder for a single photo.
class PhotoViewHolder(
    private val binding: ListItemGalleryBinding,
    private val photoClickListener: PhotoGalleryFragment
) : RecyclerView.ViewHolder(binding.root) {


    fun bind(galleryItem: NasaResponse?) {
        galleryItem?.let { item ->
            binding.itemImageView.tag = item.url
            binding.itemImageView.contentDescription = item.title
            binding.itemImageView.load(item.url) {
                placeholder(R.drawable.earth)
            }
            binding.itemImageView.setOnClickListener {
                val hdImageUrl = item.hdUrl ?: item.url  // Use regular URL if HD URL is missing
                photoClickListener.onPhotoClicked(hdImageUrl, item.title) // Pass HD image URL and title
            }
        }
    }

    // PhotoListAdapter: Adapter for the RecyclerView in PhotoGalleryFragment.
    class PhotoListAdapter(private val photoClickListener: PhotoGalleryFragment) :
        PagingDataAdapter<NasaResponse, PhotoViewHolder>(GalleryItemComparator) {

        interface PhotoClickListener {
            fun onPhotoClicked(imageUrl: String, imageTitle: String)
        }
        // onCreateViewHolder: Creates new ViewHolder for RecyclerView items.
        override fun onCreateViewHolder(
            parent: ViewGroup, viewType: Int
        ): PhotoViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ListItemGalleryBinding.inflate(inflater, parent, false)
            return PhotoViewHolder(binding, photoClickListener)
        }

        // onBindViewHolder: Binds data to an existing ViewHolder.
        override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
            val item = getItem(position)
            holder.bind(item)
        }

        object GalleryItemComparator : DiffUtil.ItemCallback<NasaResponse>() {
            override fun areItemsTheSame(oldItem: NasaResponse, newItem: NasaResponse): Boolean {
                return oldItem.date == newItem.date
            }

            override fun areContentsTheSame(oldItem: NasaResponse, newItem: NasaResponse): Boolean {
                return oldItem == newItem
            }
        }
    }
}


