package com.learning.newsapiclient.presentation.di

import com.learning.newsapiclient.data.repository.NewsRepositoryImpl
import com.learning.newsapiclient.data.repository.dataSource.NewsLocalDataSource
import com.learning.newsapiclient.data.repository.dataSource.NewsRemoteDataSource
import com.learning.newsapiclient.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideNewsRepository(
        newsRemoteDataSource: NewsRemoteDataSource,
        newsLocalDataSource: NewsLocalDataSource,
    ): NewsRepository {
        return NewsRepositoryImpl(newsRemoteDataSource, newsLocalDataSource)
    }
}