package com.learning.newsapiclient.data.repository.dataSourceImpl

import com.learning.newsapiclient.data.db.ArticleDAO
import com.learning.newsapiclient.data.model.Article
import com.learning.newsapiclient.data.repository.dataSource.NewsLocalDataSource

class NewsLocalDataSourceImpl(private val articleDAO: ArticleDAO) : NewsLocalDataSource{

    override suspend fun saveArticleToDB(article: Article) {
        // in order to save data in data base we need DAO Interface
        articleDAO.insert(article)

    }

}