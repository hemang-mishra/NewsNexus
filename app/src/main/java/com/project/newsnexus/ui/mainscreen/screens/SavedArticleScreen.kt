package com.project.newsnexus.ui.mainscreen.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.project.newsnexus.R
import com.project.newsnexus.data.model.Article
import com.project.newsnexus.ui.mainscreen.viemodel.MainViewModel

@Composable
fun SavedArticlesScreen(
    modifier: Modifier,
    viewModel: MainViewModel
) {
    val articles by viewModel.savedArticles.collectAsState(initial = emptyList())
    Column {
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, bottom = 4.dp)){
            Column {
                Text(text = "Saved Articles", style = MaterialTheme.typography.headlineMedium)
                Text(text = "Your saved articles will appear here", style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                ))
            }
        }
        if(articles.isNotEmpty()) {
            ListView(
                articles = articles,
                modifier = modifier,
                saveArticle = { viewModel.saveArticle(it) },
                unSaveArticle = { viewModel.unSaveArticle(it) },
                viewModel = viewModel,
                likeArticle = { viewModel.likeArticle(it) }
            )
        }else{
            LottieAnimationComposable(modifier = Modifier, resource = R.raw.emptylist, text = "No saved articles found.")
        }
    }
}