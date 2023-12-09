package com.steinmetz.msu.nasaphotogallery

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import coil.load

class ImageDisplayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_display)

        val imageUrl = intent.getStringExtra("IMAGE_URL")
        val imageTitle = intent.getStringExtra("IMAGE_TITLE")

        val imageView = findViewById<ImageView>(R.id.full_image_view)
        val titleView = findViewById<TextView>(R.id.image_title)

        imageView.load(imageUrl) {
            placeholder(R.drawable.earth) // Placeholder image
        }
        titleView.text = imageTitle
    }
}