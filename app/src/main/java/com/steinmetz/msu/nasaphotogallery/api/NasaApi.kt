package com.steinmetz.msu.nasaphotogallery.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface NasaApi {
    @GET("planetary/apod")
    suspend fun fetchPhoto(
        @Query("api_key") apiKey: String,
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String
    ): Response<List<NasaResponse>>
}