package com.example.countrynewsdisplayerapp.ui.fragment.detail

import android.app.Application
import androidx.lifecycle.*
import com.example.countrynewsdisplayerapp.base.BaseState
import com.example.countrynewsdisplayerapp.data.Article
import com.example.countrynewsdisplayerapp.data.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsDetailViewModel: ViewModel() {
    private val state = MutableLiveData<BaseState>()
    fun getState(): LiveData<BaseState> = state

    fun setupParams(article: Article) {
        state.postValue(BaseState.Normal(NewsDetailState(article)))
    }

}