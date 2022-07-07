package com.learning.newsapiclient.presentation.di

import com.learning.newsapiclient.domain.repository.NewsRepository
import com.learning.newsapiclient.domain.usecase.GetNewsHeadLinesUseCase
import com.learning.newsapiclient.domain.usecase.GetSavedNewsUseCase
import com.learning.newsapiclient.domain.usecase.GetSearchedNewsUseCase
import com.learning.newsapiclient.domain.usecase.SaveNewsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Singleton
    @Provides
    fun providesGetNewsheadLinesUseCase(repository: NewsRepository): GetNewsHeadLinesUseCase {
        return GetNewsHeadLinesUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesGetSearchedNewsUseCase(repository: NewsRepository): GetSearchedNewsUseCase {
        return GetSearchedNewsUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideSaveNewsUseCase(repository: NewsRepository): SaveNewsUseCase {
        return SaveNewsUseCase(repository)

    }
}