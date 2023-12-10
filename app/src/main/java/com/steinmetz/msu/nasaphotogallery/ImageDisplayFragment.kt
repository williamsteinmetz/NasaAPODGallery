package com.steinmetz.msu.nasaphotogallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import coil.load

class ImageDisplayFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image_display, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve arguments
        val imageUrl = arguments?.getString("IMAGE_URL")
        val imageTitle = arguments?.getString("IMAGE_TITLE")

        // Find views
        val imageView = view.findViewById<ImageView>(R.id.full_image_view)
        val titleView = view.findViewById<TextView>(R.id.image_title)

        // Set image and title
        imageView.load(imageUrl) {
            placeholder(R.drawable.earth) // Placeholder image
        }
        titleView.text = imageTitle
    }

    companion object {
        // Factory method to create a new instance of this fragment with provided parameters
        fun newInstance(imageUrl: String, imageTitle: String) = ImageDisplayFragment().apply {
            arguments = Bundle().apply {
                putString("IMAGE_URL", imageUrl)
                putString("IMAGE_TITLE", imageTitle)
            }
        }
    }
}