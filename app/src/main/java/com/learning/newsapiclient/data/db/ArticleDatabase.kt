package com.learning.newsapiclient.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.learning.newsapiclient.data.model.Article

@Database(
    entities = [Article::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(Convertors::class)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun getArticleDao():ArticleDAO
}