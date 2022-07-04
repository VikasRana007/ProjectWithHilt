package com.learning.newsapiclient.domain.usecase

import com.learning.newsapiclient.data.model.Article
import com.learning.newsapiclient.domain.repository.NewsRepository

class DeleteSavedNewsUseCase(private val newsRepository: NewsRepository) {
    suspend fun execute(article:Article) = newsRepository.deleteNews(article)
}