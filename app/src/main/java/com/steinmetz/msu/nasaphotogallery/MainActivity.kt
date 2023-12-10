package com.steinmetz.msu.nasaphotogallery

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.steinmetz.msu.nasaphotogallery.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), PhotoViewHolder.PhotoListAdapter.PhotoClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the PhotoGalleryFragment if the activity is newly created
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, PhotoGalleryFragment()).commit()
        }
    }

    override fun onPhotoClicked(imageUrl: String, imageTitle: String) {
        // Handle the photo click event and display the ImageDisplayFragment
        val fragment = ImageDisplayFragment.newInstance(imageUrl, imageTitle)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
            .addToBackStack(null) // Add this transaction to the back stack
            .commit()
    }
}