package com.learning.newsapiclient.data.repository.dataSource

import com.learning.newsapiclient.data.model.APIResponse
import retrofit2.Response

interface NewsRemoteDataSource {
    // inside this function we will define abstract function to communicate with api
    suspend fun getTopHeadlines(country : String, page : Int):Response<APIResponse>

    suspend fun getTopSearchedNews(country : String, searchQuery:String, page : Int):Response<APIResponse>
}