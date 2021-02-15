package com.example.countrynewsdisplayerapp.data.network

import com.example.countrynewsdisplayerapp.data.NewsResponseDataModel
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("v2/top-headlines")
    suspend fun getCountryNews(@Query("country") countryName: String, @Query("apiKey") apiKey: String): NewsResponseDataModel
}