package com.steinmetz.msu.nasaphotogallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.steinmetz.msu.nasaphotogallery.api.NasaResponse
import kotlinx.coroutines.flow.Flow

// Sets the amount of items to display during each "page" load.
private const val ITEMS_PER_PAGE = 10

// PhotoGalleryViewModel: ViewModel for managing the data of the PhotoGalleryFragment.
class PhotoGalleryViewModel : ViewModel() {
    private val photoRepository = PhotoRepository()

    // Flow to hold and observe photo data.
    // Uses a Repository to fetch data.
    val galleryItems: Flow<PagingData<NasaResponse>> =
        Pager(config = PagingConfig(pageSize = ITEMS_PER_PAGE, enablePlaceholders = false),
            pagingSourceFactory = { photoRepository.galleryItemPagingSource() }).flow.cachedIn(
            viewModelScope
        )
}