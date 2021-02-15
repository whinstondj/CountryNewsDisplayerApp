package com.example.countrynewsdisplayerapp.data.network

import com.example.countrynewsdisplayerapp.data.NewsResponseDataModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsNetwork {
    lateinit var service: NewsService

    private fun loadRetrofit() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(NewsService::class.java)
    }
    suspend fun requestCountryNews(country: String, apiKey: String): NewsResponseDataModel {
        loadRetrofit()
        return service.getCountryNews(country, apiKey)
    }
}