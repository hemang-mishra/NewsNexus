package com.project.newsnexus.data.repository

import com.project.newsnexus.data.model.Article
import com.project.newsnexus.domain.ArticlesError
import com.project.newsnexus.domain.NewsCategories
import com.project.newsnexus.domain.Resource


interface NewsRepository {
    suspend fun getNews(categories: NewsCategories = NewsCategories.GENERAL, query: String? = null): Resource<List<Article>, ArticlesError>
}