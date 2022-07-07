package com.learning.newsapiclient.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import com.learning.newsapiclient.data.model.Article

@Dao
interface ArticleDAO {

    @Insert(onConflict = REPLACE)
    suspend fun insert(article: Article)
}