package com.steinmetz.msu.nasaphotogallery.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NasaResponse(
    val date: String,
    val explanation: String,
    val title: String,
    val url: String,
    @Json(name = "media_type") val mediaType: String?, // "image" or "video"
    @Json(name = "hdurl") val hdUrl: String?
)
