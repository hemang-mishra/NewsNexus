package com.project.newsnexus.data.remote

import com.project.newsnexus.BuildConfig
import com.project.newsnexus.data.model.Article
import com.project.newsnexus.data.model.Articles
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://gnews.io/"

interface NewsAPI {

    @GET("api/v4/top-headlines")
    suspend fun callAPI(
        @Query("category") category: String = "general",
        @Query("lang") lang: String = "en",
        @Query("country") country: String = "in",
        @Query("q") q: String = "None",
        @Query("apikey") api: String = BuildConfig.GNEWS_API_KEY,
    ):Response<Articles>
}