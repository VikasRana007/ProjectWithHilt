package com.learning.newsapiclient.presentation.di

import android.app.Application
import androidx.room.Room
import com.learning.newsapiclient.data.db.ArticleDAO
import com.learning.newsapiclient.data.db.ArticleDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Provides
    @Singleton
    fun provideNewsDataBase(app:Application):ArticleDatabase{
        return Room.databaseBuilder(app,ArticleDatabase::class.java,"news_db")
            .fallbackToDestructiveMigration()     // <====== This will allow Room to destructively
            .build()                            //  replace data base tables if Migrations,
                                              // that would n=migrate old data base to the latest schema version
    }

    @Singleton
    @Provides
    fun provideNewsDao(articleDatabase: ArticleDatabase):ArticleDAO{
        return articleDatabase.getArticleDao()
    }
}