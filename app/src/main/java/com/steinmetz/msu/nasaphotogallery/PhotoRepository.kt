package com.steinmetz.msu.nasaphotogallery

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.steinmetz.msu.nasaphotogallery.api.NasaApi
import com.steinmetz.msu.nasaphotogallery.api.NasaResponse
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.time.LocalDate

private const val API_KEY = "vNS0rzNlUN2lTat0AaK25vx6aVUaeiPTe6SM35Hn"


private const val TAG = "PhotoRepository"

class PhotoRepository {
    private val nasaApi: NasaApi

    init {
        val retrofit: Retrofit = Retrofit.Builder().baseUrl("https://api.nasa.gov/")
            .addConverterFactory(MoshiConverterFactory.create()).build()
        nasaApi = retrofit.create()
    }

    fun galleryItemPagingSource() = GalleryPagingSource(nasaApi)
}

class GalleryPagingSource(private val apiService: NasaApi) : PagingSource<Int, NasaResponse>() {

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NasaResponse> {
        val currentPage = params.key ?: 0  // Current page number, starting from 0
        val totalDays = 100  // Total number of days to fetch
        val daysPerRequest =
            if (currentPage == 0) 14 else 15  // Number of days to fetch per request

        val endDate = LocalDate.now().minusDays(currentPage * daysPerRequest.toLong())
        val startDate = if (currentPage == 0) endDate.minusDays(daysPerRequest.toLong())
        else endDate.minusDays(daysPerRequest.toLong()).plusDays(1)

        return try {
            val response = apiService.fetchPhoto(API_KEY, startDate.toString(), endDate.toString())
            if (response.isSuccessful && response.body() != null) {
                val photos =
                    response.body()!!.filter { it.mediaType != "video" } // Filter out video entries
                        .reversed() // Reverse the list of photos

                val nextKey =
                    if (currentPage * daysPerRequest < totalDays) currentPage + 1 else null

                LoadResult.Page(
                    data = photos,
                    prevKey = if (currentPage > 0) currentPage - 1 else null,
                    nextKey = nextKey
                )
            } else {
                LoadResult.Error(Exception("API call failed"))
            }
        } catch (exception: Exception) {
            Log.e(TAG, "Error fetching photos", exception)
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, NasaResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}


