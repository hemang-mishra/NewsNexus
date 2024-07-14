package com.project.newsnexus.data.repository

import com.project.newsnexus.data.local.ArticleDAO
import com.project.newsnexus.data.model.Article
import kotlinx.coroutines.flow.Flow

class SavedRepositoryImpl(private val dao: ArticleDAO): SavedRepository {
    override fun getSavedArticles(): Flow<List<Article>> {
        return dao.getArticles()
    }

//    override fun getSavedArticlesWithoutFlow(): List<Article> {
//        return dao.getArticlesWithoutFlow()
//    }

    override suspend fun saveArticle(article: Article) {
        dao.upsertArticle(article)
    }

    override suspend fun deleteArticle(article: Article) {
        dao.deleteContact(article)
    }
}