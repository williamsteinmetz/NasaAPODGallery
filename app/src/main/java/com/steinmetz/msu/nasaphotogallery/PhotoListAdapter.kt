package com.steinmetz.msu.nasaphotogallery

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.steinmetz.msu.nasaphotogallery.api.NasaResponse
import com.steinmetz.msu.nasaphotogallery.databinding.ListItemGalleryBinding


class PhotoViewHolder(
    private val binding: ListItemGalleryBinding
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.itemImageView.setOnClickListener {
            // Handle the click event
            val context = binding.root.context
            val intent = Intent(context, ImageDisplayActivity::class.java).apply {
                putExtra("IMAGE_URL", binding.itemImageView.tag as? String)
                putExtra("IMAGE_TITLE", binding.itemImageView.contentDescription as? String)
            }
            context.startActivity(intent)
        }
    }

    fun bind(galleryItem: NasaResponse?) {
        galleryItem?.let { item ->
            binding.itemImageView.tag = item.url
            binding.itemImageView.contentDescription = item.title
            binding.itemImageView.load(item.url) {
                placeholder(R.drawable.earth)
            }
        }
    }
}

class PhotoListAdapter : PagingDataAdapter<NasaResponse, PhotoViewHolder>(GalleryItemComparator) {
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): PhotoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemGalleryBinding.inflate(inflater, parent, false)
        return PhotoViewHolder(binding)
    }

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


