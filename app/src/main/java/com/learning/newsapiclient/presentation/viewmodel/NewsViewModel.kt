package com.learning.newsapiclient.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.learning.newsapiclient.data.model.APIResponse
import com.learning.newsapiclient.data.model.Article
import com.learning.newsapiclient.data.util.Resource
import com.learning.newsapiclient.domain.usecase.GetNewsHeadLinesUseCase
import com.learning.newsapiclient.domain.usecase.GetSearchedNewsUseCase
import com.learning.newsapiclient.domain.usecase.SaveNewsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel(
    private val app: Application,
    private val getNewsHeadLinesUseCase: GetNewsHeadLinesUseCase,
    private val getSearchedNewsUseCase: GetSearchedNewsUseCase,
    private val saveNewsUseCase: SaveNewsUseCase
) : AndroidViewModel(app) {
    // in this class we are to write a function to get the list of news headlines.
    // we will get it as a mutable live data of type Resource
    val newsHeadLines: MutableLiveData<Resource<APIResponse>> = MutableLiveData()

    fun getNewsHeadLines(country: String, page: Int) = viewModelScope.launch(Dispatchers.IO) {
        newsHeadLines.postValue(Resource.Loading())
        try {
            if (isNetworkAvailable(app)) {
//                Log.i("ADD TAG", "Network Availability")
                val apiResult = getNewsHeadLinesUseCase.execute(country, page)
                newsHeadLines.postValue(apiResult)
            } else {
                newsHeadLines.postValue(Resource.Error("Internet is not available"))
            }
        } catch (e: Exception) {
            newsHeadLines.postValue(Resource.Error(e.message.toString()))
        }
    }

    // searched data
    val searchedNews: MutableLiveData<Resource<APIResponse>> = MutableLiveData()

    fun searchNews(country: String, searchQuery: String, page: Int) = viewModelScope.launch(Dispatchers.IO) {
            searchedNews.postValue(Resource.Loading())
            try {
                if (isNetworkAvailable(app)) {
                   val response = getSearchedNewsUseCase.execute(country, searchQuery, page)
                    searchedNews.postValue(response)          // post value to mutable live data (searchedNews)
                } else {
                    searchedNews.postValue(Resource.Error("No Internet Connection"))
                }

            } catch (E: Exception) {
                searchedNews.postValue(Resource.Error(E.message.toString()))
            }
        }

    private fun isNetworkAvailable(context: Context?):Boolean{
        if (context == null) return false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false

    }

    // local data base
     fun saveArticle(article: Article) = viewModelScope.launch {
        saveNewsUseCase.execute(article)
    }
}