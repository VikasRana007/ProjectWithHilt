package com.learning.newsapiclient.data.api

import com.learning.newsapiclient.BuildConfig
import com.learning.newsapiclient.data.model.APIResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPIService {
    // Here define functions to get data from the news api with url end points & query parameters

    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country:String,
        @Query("page") page:Int,
        @Query("apiKey") apiKey:String = BuildConfig.API_KEY): Response<APIResponse>

    @GET("v2/top-headlines")
    suspend fun getSearchedTopHeadlines(
        @Query("country") country:String,
        @Query("q") searchQuery:String,
        @Query("page") page:Int,
        @Query("apiKey") apiKey:String = BuildConfig.API_KEY): Response<APIResponse>
}