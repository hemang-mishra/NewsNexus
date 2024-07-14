package com.project.newsnexus.ui.mainscreen.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.project.newsnexus.data.model.Article
import com.project.newsnexus.ui.mainscreen.viemodel.MainViewModel


@OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ListView(
    articles: List<Article>,
    viewModel: MainViewModel,
    modifier: Modifier,
    saveArticle:(Article)->Unit,
    unSaveArticle: (Article)->Unit,
    top: @Composable BoxScope.() -> Unit = {},
    onRefresh: () -> Unit = {},
    likeArticle: (Article) -> Unit
) {
    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()

    BackHandler(navigator.canNavigateBack()) {
        navigator.navigateBack()
    }
    NavigableListDetailPaneScaffold(navigator = navigator,
        modifier = modifier,
        listPane = {
            Column(
            ) {
                Surface(modifier = Modifier.fillMaxWidth()) {
                    Box {
                        top()
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {

                    LazyColumn(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.preferredWidth(400.dp),
                    ) {
                        items(articles.size) {
                            NewsCell(article = articles[it],
                                saveArticle = { saveArticle(articles[it]) },
                                unSaveArticle = { unSaveArticle(articles[it]) }
                                , modifier = Modifier.clickable {
                                navigator.navigateTo(
                                    ListDetailPaneScaffoldRole.Detail,
                                    content = articles[it]
                                )
                            }
                            ) {
                                likeArticle(articles[it])
                            }

                        }
                    }
                }

            }
        },
        detailPane = {
            val content = navigator.currentDestination?.content as Article?
            val article = articles.find { it.url == content?.url }
            AnimatedPane {
                DetailsScreen(article = article, viewModel =viewModel , onBack={
                    navigator.navigateBack()
                }) {
                    navigator.navigateTo(ListDetailPaneScaffoldRole.Extra, content = content)
                }
            }
        },
        extraPane = {
            val article = navigator.currentDestination?.content as Article?
            AnimatedPane {
                WebViewScreen(url = article?.url)
            }
        })
}