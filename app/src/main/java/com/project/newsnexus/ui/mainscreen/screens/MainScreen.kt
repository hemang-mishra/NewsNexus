package com.project.newsnexus.ui.mainscreen.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.project.newsnexus.ui.mainscreen.viemodel.MainViewModel


@Composable
fun MainScreen(viewModel: MainViewModel) {
    val uiState by viewModel.uiState

    NavigationSuiteScaffold(
        modifier = Modifier,
        navigationSuiteItems = {
        NavigationItems.entries.forEachIndexed { index, navigationItem ->
            val isSelected = uiState.selectedScreen == index
            item(
                selected = isSelected,
                onClick = { viewModel.changeScreen(selectedScreen = index) },
                icon = {
                    Icon(
                        imageVector = if (isSelected) navigationItem.iconSelected else navigationItem.iconNotSelected,
                        contentDescription = null
                    )
                },
                label = {
                    Text(text = navigationItem.displayName)
                })
        }
    }) {
        Scaffold(
            bottomBar = {

            },
        ) { innerpadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerpadding)
            ) {
                AnimatedContent(targetState = uiState.selectedScreen) { index ->
                    when (index) {
                        0 -> HomeScreen(
                            articles = uiState.articles,
                            viewModel = viewModel,
                            modifier = Modifier
                        ) {
                            viewModel.likeArticle(article = it)
                        }

                        1 -> SavedArticlesScreen(modifier = Modifier, viewModel = viewModel)
                    }
                }
            }
        }
    }
}
