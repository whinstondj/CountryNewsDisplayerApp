package com.example.countrynewsdisplayerapp.ui.fragment.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.countrynewsdisplayerapp.base.BaseState
import com.example.countrynewsdisplayerapp.data.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsListViewModel(app:Application): AndroidViewModel(app) {
    private val state = MutableLiveData<BaseState>()
    fun getState(): LiveData<BaseState> = state

    fun requestInformation(country:String) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    state.postValue(BaseState.Loading())
                    val items = NewsRepository().requestNewsCountry(country,"75bffc8c8ee3419e835c9caae2ebd0a7")
                    state.postValue(BaseState.Normal(NewsListState(items)))
                } catch (e: Exception) {
                    state.postValue(BaseState.Error(e))
                }
            }
    }
}