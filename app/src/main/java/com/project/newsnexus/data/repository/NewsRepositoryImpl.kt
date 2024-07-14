package com.project.newsnexus.data.repository

import android.util.Log
import com.project.newsnexus.data.model.Article
import com.project.newsnexus.data.remote.NewsAPI
import com.project.newsnexus.domain.ArticlesError
import com.project.newsnexus.domain.NewsCategories
import com.project.newsnexus.domain.Resource
import com.project.newsnexus.ui.mainscreen.screens.article
import kotlinx.coroutines.delay
import retrofit2.HttpException

class NewsRepositoryImpl(private val api: NewsAPI): NewsRepository {

//        override suspend fun getNews(categories: NewsCategories, query: String?): Resource<List<Article>,ArticlesError> {
//        return try {
//            delay(3000)
////            throw Exception()
//            Resource.Success(listOf(article, article, article, article, article, article))
//        }
//        catch (e: Exception){
//            Resource.Error(ArticlesError.NetworkError.NO_INTERNET)
//        }
//    }
    override suspend fun getNews(categories: NewsCategories, query: String?): Resource<List<Article>, ArticlesError> {
        return try {
            val response = api.callAPI(category = categories.value, q = query?:"None")
            Log.i("response", "$response")
            if(response.isSuccessful.not()){
                throw HttpException(response)
            }
            val articleList =response.body()?:throw Exception()
            Log.i("fetched", "$articleList")
            Resource.Success(articleList.articles)
        }catch (e: HttpException){
            if(e.code() == 401){
                Resource.Error(ArticlesError.NetworkError.NO_INTERNET)
            }
            else if (e.code() == 408){
                Resource.Error(ArticlesError.NetworkError.TIMEOUT)
            }
            else if (e.code() == 500){
                Resource.Error(ArticlesError.NetworkError.SERVER_NOT_RESPONDING)
            }
            else{
                Resource.Error(ArticlesError.NetworkError.UNKNOWN)
            }
        }
        catch (e: Exception){
            Log.i("hi","h")
            Resource.Error(ArticlesError.NetworkError.UNKNOWN)
        }
    }
}