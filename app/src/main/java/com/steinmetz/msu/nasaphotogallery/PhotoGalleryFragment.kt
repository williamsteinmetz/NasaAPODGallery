package com.steinmetz.msu.nasaphotogallery

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.steinmetz.msu.nasaphotogallery.databinding.FragmentPhotoGalleryBinding
import kotlinx.coroutines.launch

private const val TAG = "PhotoGalleryFragment"

class PhotoGalleryFragment : Fragment() {
    private var _binding: FragmentPhotoGalleryBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val nasaGalleryViewModel: PhotoGalleryViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhotoGalleryBinding.inflate(inflater, container, false)
        binding.photoGrid.layoutManager = GridLayoutManager(context, 3)
        Log.d(TAG, "FragmentPhotoGalleryBinding Set")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = PhotoListAdapter()

        binding.photoGrid.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                Log.d(TAG, "Inside lifecycleScope.launch")
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    Log.d(TAG, "Inside repeatOnLifecycle")
                    nasaGalleryViewModel.galleryItems.collect { pagingData ->
                        Log.d(TAG, "Paging Data: $pagingData")
                        adapter.submitData(pagingData)
                        Log.d(TAG, "Inside collect: Data Received")
                    }
                }
            } catch (e: Exception) {
                Log.d(TAG, "EXCEPTION")
                Log.e(TAG, e.toString())
            }
        }
        Log.d(TAG, "onViewCreated: Coroutine setup complete")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
