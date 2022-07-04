package com.learning.newsapiclient.domain.repository

import com.learning.newsapiclient.data.model.APIResponse
import com.learning.newsapiclient.data.model.Article
import com.learning.newsapiclient.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    // also consider the state of APIResponse
    //These function will use the for network communication
    suspend fun getNewsHeadlines(country: String, page: Int): Resource<APIResponse>
    suspend fun getSearchedNews(country: String,searchQuery: String, page: Int): Resource<APIResponse>

    // functions related to the local data
    suspend fun saveNews(article: Article)
    suspend fun deleteNews(article: Article)
    fun getSavedNews(): Flow<List<Article>>
    /**
     * Flow api in kotlin is a better way to handle the stream of data asynchronously
     * we can also use Live data here but it is not best practice because Live data
     * should only be use emit data from view model to UI component (Activity, Fragment)
     * and before coroutine flow developers use there RxJava for Data flow. Since
     * this function returns a data stream we don't need to write this function as
     * suspend function, we don't need to pause and resume this function at a later time.
     */

    /**
     * In the clean architecture we don't use android framework related libraries in the domain layer
     * that means in repository interface & in use case classes we can only import kotlin related classes
     * and our own classes.
     */
}