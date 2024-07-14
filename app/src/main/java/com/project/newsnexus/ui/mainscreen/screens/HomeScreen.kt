package com.project.newsnexus.ui.mainscreen.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.project.newsnexus.R
import com.project.newsnexus.data.model.Article
import com.project.newsnexus.data.model.Source
import com.project.newsnexus.domain.ArticlesError
import com.project.newsnexus.domain.NewsCategories
import com.project.newsnexus.ui.mainscreen.viemodel.MainViewModel
import com.project.newsnexus.utils.isOnline
import kotlinx.coroutines.delay

val source = Source(
    url = "www.youtube.com",
    name = "Dummy News"
)

val article = Article(
    content = "This is the full content of the dummy article. It provides in-depth information about the topic discussed in the article.",
    description = "This is a short description of the dummy article, summarizing its main points.",
    image = "https://img-s-msn-com.akamaized.net/tenant/amp/entityid/BB1pOttF.img?w=768&h=432&m=6&x=356&y=130&s=105&d=105",
    publishedAt = "2024-07-12T10:00:00Z",
    source = source,
    title = "Budget 2024: Income Tax Expectations From Finance Minister Nirmala Sitharaman",
    url = "https://medium.com/@kevinnzou/using-webview-in-jetpack-compose-bbf5991cfd14"
)

@Composable
fun HomeScreen(
    modifier: Modifier,
    articles: List<Article>,
    viewModel: MainViewModel,
    likeArticle: (Article) -> Unit
) {
    val context = LocalContext.current
    Column(
        modifier=Modifier.padding()
    ) {
        if (viewModel.uiState.value.isLoading) {
            LottieAnimationComposable(
                modifier = Modifier.fillMaxSize(),
                resource = R.raw.news_loading_animation,
                speed = 8f
            )
        } else if (viewModel.uiState.value.errorType != null) {
            if (!isOnline(context)) {
                LottieAnimationComposable(
                    modifier = modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.7f),
                    resource = R.raw.offlinelottie,
                    text = "No Internet. Please turn on Internet.",
                    isButtonEnabled = true,
                ) {
                    viewModel.retryFetchTopHeadlinesNews()
                }

            } else
                when (val error = viewModel.uiState.value.errorType) {
                    is ArticlesError.NetworkError -> {
                        when (error) {
                            ArticlesError.NetworkError.NO_INTERNET -> {
                                LottieAnimationComposable(
                                    modifier = modifier
                                        .fillMaxWidth()
                                        .fillMaxHeight(0.7f),
                                    resource = R.raw.offlinelottie,
                                    text = "No Internet. Please turn on Internet.",
                                    isButtonEnabled = true,
                                ) {
                                    viewModel.retryFetchTopHeadlinesNews()
                                }
                            }

                            ArticlesError.NetworkError.TIMEOUT -> {
                                LottieAnimationComposable(
                                    modifier = modifier
                                        .fillMaxWidth()
                                        .fillMaxHeight(0.7f),
                                    resource = R.raw.offlinelottie,
                                    text = "Timeout. Please try again.",
                                    isButtonEnabled = true,
                                ) {
                                    viewModel.retryFetchTopHeadlinesNews()
                                }
                            }

                            ArticlesError.NetworkError.SERVER_NOT_RESPONDING -> {
                                LottieAnimationComposable(
                                    modifier = modifier
                                        .fillMaxWidth()
                                        .fillMaxHeight(0.7f),
                                    resource = R.raw.offlinelottie,
                                    text = "Server not responding. Please try again.",
                                    isButtonEnabled = true,
                                ) {
                                    viewModel.retryFetchTopHeadlinesNews()
                                }
                            }

                            ArticlesError.NetworkError.UNKNOWN -> {
                                LottieAnimationComposable(
                                    modifier = modifier
                                        .fillMaxWidth()
                                        .fillMaxHeight(0.7f),
                                    resource = R.raw.offlinelottie,
                                    text = "Unknown error. Please try again.",
                                    isButtonEnabled = true,
                                ) {
                                    viewModel.retryFetchTopHeadlinesNews()
                                }
                            }

                            ArticlesError.NOT_FOUND -> {
                                LottieAnimationComposable(
                                    modifier = modifier
                                        .fillMaxWidth()
                                        .fillMaxHeight(0.7f),
                                    resource = R.raw.notfouondlottie,
                                    text = "No news found.",
                                    isButtonEnabled = true,
                                ) {
                                    viewModel.retryFetchTopHeadlinesNews()
                                }

                            }
                        }
                    }

                    else -> {
                        LottieAnimationComposable(
                            modifier = modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.7f),
                            resource = R.raw.offlinelottie,
                            text = "Unknown error. Please try again.",
                            isButtonEnabled = true,
                        ) {
                            viewModel.retryFetchTopHeadlinesNews()
                        }
                    }
                }
        } else {
            ListView(articles = articles, modifier = modifier,
                saveArticle = { viewModel.saveArticle(it) },
                unSaveArticle = { viewModel.unSaveArticle(it) },
                viewModel = viewModel,
                top = {
                    Column {
                        TopSearchBar(
                            categories = viewModel.uiState.value.currentCategories,
                            onSearch = {
                                viewModel.search(it)
                            })
                        LazyRow {
                            item() {
                                NewsCategories.entries.forEach {category->
                                    val isSelected =
                                        category == viewModel.uiState.value.currentCategories
                                    if (isSelected)
                                        CategoryChip(
                                            category = category,
                                            isSelected = true,
                                            onSelected = {
                                                viewModel.changeCategory(category)
                                            }
                                        )
                                }
                            }
                            items(NewsCategories.entries.size){index->
                                val category = NewsCategories.entries[index]
                                val isSelected = category == viewModel.uiState.value.currentCategories
                                if(!isSelected)
                                CategoryChip(
                                    category = category,
                                    isSelected = isSelected,
                                    onSelected = {
                                        viewModel.changeCategory(category)
                                    }
                                )
                            }
                        }
                    }
                }) {
                likeArticle(it)
            }
        }
    }
}

@Composable
fun CategoryChip(
    category: NewsCategories,
    isSelected: Boolean,
    onSelected: (NewsCategories) -> Unit
) {
    FilterChip(selected = isSelected, onClick ={ onSelected(category)},
        modifier = Modifier.padding(horizontal = 4.dp),label = {
        Text(text = category.displayName)
    })
}


@Composable
private fun TopSearchBar(onSearch: (String) -> Unit, categories: NewsCategories) {
    var query by remember {
        mutableStateOf("")
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 4.dp)
    ) {
        OutlinedTextField(
            value = query, onValueChange = { query = it },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearch(query)
                },
            ),
            shape = RoundedCornerShape(32.dp),
            modifier = Modifier
                .fillMaxWidth(), leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
            },
            placeholder = {
                InfiniteTextTransition(categories = categories)
            },
            trailingIcon = {
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.newspaper_5479355),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

        )
    }
}

@Composable
fun InfiniteTextTransition(categories: NewsCategories) {
    val texts = listOf(stringResource(id = R.string.app_name), "Top Headlines", categories.displayName)
    var currentText by remember {
        mutableStateOf(0)
    }
    LaunchedEffect(key1 = Unit) {
        while (true) {
            delay(2000L)
            currentText = (currentText + 1) % texts.size
        }
    }

    AnimatedContent(targetState = currentText,
        transitionSpec = {
            fadeIn(animationSpec = tween(500)) + slideInVertically() togetherWith
                    fadeOut(animationSpec = tween(500)) + slideOutVertically()
        }
    ) {
        Text(text = texts[it], style = MaterialTheme.typography.headlineSmall)
    }
}