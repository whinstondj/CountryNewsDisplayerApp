package com.example.countrynewsdisplayerapp.ui.fragment.list

import com.example.countrynewsdisplayerapp.data.Article
import java.io.Serializable

data class NewsListState(
    val newsList: List<Article> = listOf()
) : Serializable
