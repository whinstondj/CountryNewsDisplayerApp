package com.example.countrynewsdisplayerapp.data

import com.example.countrynewsdisplayerapp.data.network.NewsNetwork

class NewsRepository {
    suspend fun requestNewsCountry(country: String, apiKey: String): List<Article>{
        return NewsNetwork().requestCountryNews(country,apiKey).articles
    }
}