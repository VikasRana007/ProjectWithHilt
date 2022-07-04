package com.learning.newsapiclient.presentation.di

import android.app.Application
import com.learning.newsapiclient.domain.usecase.GetNewsHeadLinesUseCase
import com.learning.newsapiclient.domain.usecase.GetSearchedNewsUseCase
import com.learning.newsapiclient.presentation.viewmodel.NewsViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {

    @Provides
    @Singleton
    fun provideNewsViewModelFactory(
        application: Application,
        getNewsHeadLinesUseCase: GetNewsHeadLinesUseCase,
        getSearchedNewsUseCase: GetSearchedNewsUseCase
    ): NewsViewModelFactory {
        return NewsViewModelFactory(application, getNewsHeadLinesUseCase, getSearchedNewsUseCase)
    }
}