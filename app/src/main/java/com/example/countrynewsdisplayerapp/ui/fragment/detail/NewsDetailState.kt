package com.example.countrynewsdisplayerapp.ui.fragment.detail

import com.example.countrynewsdisplayerapp.data.Article
import java.io.Serializable

data class NewsDetailState(
    val article: Article? = null
) : Serializable
