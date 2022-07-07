package com.learning.newsapiclient.data.repository.dataSource

import com.learning.newsapiclient.data.model.Article

interface NewsLocalDataSource {
    suspend fun saveArticleToDB(article: Article)
}