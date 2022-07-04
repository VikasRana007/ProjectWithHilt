package com.learning.newsapiclient.domain.usecase

import com.learning.newsapiclient.data.model.APIResponse
import com.learning.newsapiclient.data.util.Resource
import com.learning.newsapiclient.domain.repository.NewsRepository

class GetSearchedNewsUseCase(private val newsRepository: NewsRepository) {
    suspend fun execute(country : String, searchQuery : String, page : Int): Resource<APIResponse> {
        return newsRepository.getSearchedNews(country, searchQuery, page)
    }
}