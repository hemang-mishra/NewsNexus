package com.project.newsnexus.data.repository

import com.project.newsnexus.data.model.Article
import kotlinx.coroutines.flow.Flow

interface SavedRepository {
    fun getSavedArticles(): Flow<List<Article>>

//    fun getSavedArticlesWithoutFlow(): List<Article>

    suspend fun saveArticle(article:Article)

    suspend fun deleteArticle(article: Article)
}