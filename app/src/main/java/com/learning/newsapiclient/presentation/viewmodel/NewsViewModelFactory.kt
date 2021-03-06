package com.learning.newsapiclient.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.learning.newsapiclient.domain.usecase.GetNewsHeadLinesUseCase
import com.learning.newsapiclient.domain.usecase.GetSearchedNewsUseCase
import com.learning.newsapiclient.domain.usecase.SaveNewsUseCase

class NewsViewModelFactory(
    private val app: Application,
    private val getNewsHeadLinesUseCase: GetNewsHeadLinesUseCase,
    private val getSearchedNewsUseCase: GetSearchedNewsUseCase,
    private val saveNewsUseCase: SaveNewsUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return NewsViewModel(app, getNewsHeadLinesUseCase, getSearchedNewsUseCase,saveNewsUseCase) as T
    }
}