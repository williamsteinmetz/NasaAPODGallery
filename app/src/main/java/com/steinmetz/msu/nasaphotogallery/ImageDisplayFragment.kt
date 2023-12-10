package com.steinmetz.msu.nasaphotogallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import coil.load
import coil.size.ViewSizeResolver


// ImageDisplayFragment: A Fragment to display a single image with its title.
class ImageDisplayFragment : Fragment() {

    // onCreateView: Inflates the layout for this fragment.
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image_display, container, false)
    }

    // onViewCreated: Called after the view is created.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve and display the image and title passed as arguments.
        val imageUrl = arguments?.getString("IMAGE_URL")
        val imageTitle = arguments?.getString("IMAGE_TITLE")

        // Load the image and set the title.
        val imageView = view.findViewById<ImageView>(R.id.full_image_view)
        val loadingText: TextView = view.findViewById(R.id.loading_text)
        val titleView = view.findViewById<TextView>(R.id.image_title)

        // Set image and title
        imageView.load(imageUrl) {
            size(ViewSizeResolver(imageView))  // Automatically determines a suitable size
            placeholder(R.drawable.earth) // Placeholder image
            listener(onSuccess = { _, _ ->
                // Hide the loading text when the image is successfully loaded
                loadingText.visibility = View.GONE
                titleView.visibility = View.VISIBLE
            })
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