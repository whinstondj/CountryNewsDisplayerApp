package com.example.countrynewsdisplayerapp.data

import java.io.Serializable

data class NewsResponseDataModel(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)

data class Article(
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String
) : Serializable

data class Source(
    val id: Any,
    val name: String
)
