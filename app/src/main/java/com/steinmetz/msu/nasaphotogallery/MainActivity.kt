package com.steinmetz.msu.nasaphotogallery

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.steinmetz.msu.nasaphotogallery.databinding.ActivityMainBinding

// MainActivity: The main activity that hosts different fragments.
class MainActivity : AppCompatActivity(), PhotoViewHolder.PhotoListAdapter.PhotoClickListener {

    private lateinit var binding: ActivityMainBinding

    // onCreate: Initializes the activity.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        // Set the content view from XML layout.
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

// Fragments (ImageDisplayFragment, PhotoGalleryFragment): Manage UI and user interactions.
// Activity (MainActivity): Hosts the fragments and handles navigation.
// ViewModel (PhotoGalleryViewModel): Manages data for the UI.
// Adapter (PhotoListAdapter): Connects data to RecyclerView for display.
// Repository (PhotoRepository): Handles data fetching and business logic.