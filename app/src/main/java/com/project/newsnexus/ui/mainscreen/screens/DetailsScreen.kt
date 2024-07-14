package com.project.newsnexus.ui.mainscreen.screens

import android.view.ViewGroup
import android.webkit.WebView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.SubcomposeAsyncImage
import com.project.newsnexus.data.model.Article
import com.project.newsnexus.ui.mainscreen.viemodel.MainViewModel
import com.project.newsnexus.utils.TimeUtil

@Composable
fun DetailsScreen(article: Article?, viewModel: MainViewModel,onBack: () -> Unit, onNext: () -> Unit) {
    val context = LocalContext.current
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        if (article != null) {
            item {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f)),

                ) {
                    Row(
                        modifier = Modifier
                            .align(Alignment.TopStart),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { onBack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                contentDescription = null
                            )
                        }
                        Text(
                            text = article.source.name,
                            style = MaterialTheme.typography.headlineSmall,
                            maxLines = 1,
                            modifier = Modifier.fillMaxWidth(0.6f)
                        )
                    }
                    Buttons(
                        modifier = Modifier.align(Alignment.CenterEnd),
                        likeArticle = { viewModel.likeArticle(article)},
                        saveArticle = { viewModel.saveArticle(article) },
                        unSaveArticle = { viewModel.unSaveArticle(article) },
                        article = article,
                        context = context
                    )
                }
            }
            item {
                Column {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = article.title,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(8.dp)
                    )
                    Text(
                        text = article.description,
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = MaterialTheme.colorScheme.onSurface.copy(
                                alpha = 0.9f
                            )
                        ),
                        modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                    )
                    Row {
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Outlined.Timer, contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface.copy(
                                alpha = 0.6f
                            ),
                        )
                        Text(
                            text = TimeUtil.formatDateTime(article.publishedAt),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onSurface.copy(
                                    alpha = 0.6f
                                )
                            ),
                            modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(48.dp))
                    SubcomposeAsyncImage(
                        model = article.image, contentDescription = null, loading = {
                            Icon(imageVector = Icons.Default.Image, contentDescription = null)
                        },
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .widthIn(300.dp, 500.dp)
                    )
                }
            }
            item {
                Column {

                    Text(
                        text = article.content,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(8.dp)
                    )
                    OutlinedButton(onClick = { onNext() },
                        modifier = Modifier) {
                        Text(text = "Read more")
                    }
                }
            }
        }
    }
}

@Composable
fun WebViewScreen(url: String?) {
    if (url != null)
        AndroidView(factory = {
            WebView(it).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
        },
            update = {
                it.loadUrl(url)

            })
}