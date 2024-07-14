package com.project.newsnexus.di

import androidx.room.Room
import com.project.newsnexus.data.local.ArticleDatabase
import com.project.newsnexus.data.remote.BASE_URL
import com.project.newsnexus.data.remote.NewsAPI
import com.project.newsnexus.data.repository.NewsRepository
import com.project.newsnexus.data.repository.NewsRepositoryImpl
import com.project.newsnexus.data.repository.SavedRepository
import com.project.newsnexus.data.repository.SavedRepositoryImpl
import com.project.newsnexus.ui.mainscreen.viemodel.MainViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single {
        Room.databaseBuilder(androidApplication(),ArticleDatabase::class.java,"article_db")
            .fallbackToDestructiveMigration()
            .build()
    }
    single {
        get<ArticleDatabase>().getArticleDAO()
    }
    single<SavedRepository> {
        SavedRepositoryImpl(dao = get())
    }
    //This means that they occur only once in their lifetime
    single {
        //Now koin can understand and inject these dependencies whenever required
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsAPI::class.java)
    }
    //News repository is actually the interface and NewsRepository is its implementation.
    //This is how we use them
    single<NewsRepository> {
        //Now I can access it directly
        NewsRepositoryImpl(api = get())
    }
    //defining for viewmodels
    viewModel {
        MainViewModel(get(), get())
    }
}