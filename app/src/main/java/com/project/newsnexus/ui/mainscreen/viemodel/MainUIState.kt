package com.project.newsnexus.ui.mainscreen.viemodel

import com.project.newsnexus.data.model.Article
import com.project.newsnexus.domain.Error
import com.project.newsnexus.domain.NewsCategories
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class MainUIState(
    val isLoading: Boolean = false,
    val errorType: Error? = null,
    val selectedScreen: Int = 0,
    val articles: List<Article> = emptyList(),
    val currentCategories: NewsCategories = NewsCategories.GENERAL,
    val savedArticles: Flow<List<Article>> = flowOf(emptyList())
//    val items:
)

