package com.project.newsnexus.ui.mainscreen.screens

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.project.newsnexus.R
import com.project.newsnexus.data.model.Article
import com.project.newsnexus.utils.TimeUtil

//@Composable
//fun NewsGrid(modifier: Modifier = Modifier) {
////    LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 300.dp)) {
//    LazyColumn(
//        modifier = modifier,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        items(20) {
//            NewsCell(article = article)
//        }
//    }
//}


@Composable
fun NewsCell(
    article: Article,
    modifier: Modifier = Modifier,
    saveArticle: () -> Unit,
    unSaveArticle: () -> Unit,
    likeArticle: () -> Unit = {}
) {
    val context = LocalContext.current
    Column(modifier = modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.height(16.dp))
        SubcomposeAsyncImage(
            model = article.image, contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
//                .height(200.dp)
                .padding(horizontal = 24.dp)
                .clip(RoundedCornerShape(16.dp))
                .align(Alignment.CenterHorizontally),
            loading = {
                Box(modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center){
                    Icon(imageVector = Icons.Default.Image, contentDescription = null,
                        tint= MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(80.dp))
                }
            },
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = article.title,
            modifier = Modifier.padding(horizontal = 24.dp),
            maxLines = 3,
            style = MaterialTheme.typography.titleLarge
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            Row(
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Text(
                    text = article.source.name,
                    maxLines = 1,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(text = " • ${TimeUtil.getTimeDiff(article.publishedAt)}", style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)))
            }
            Buttons(
                modifier = Modifier.align(Alignment.CenterEnd),
                likeArticle = { likeArticle() },
                saveArticle = { saveArticle() },
                unSaveArticle = { unSaveArticle() },
                article = article,
                context = context
            )
        }
        HorizontalDivider(modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun Buttons(modifier: Modifier, likeArticle: () -> Unit, saveArticle: () -> Unit, unSaveArticle: () -> Unit, article: Article, context: Context){
    Row(
        modifier = modifier
    ) {
        IconButton(onClick = {
            likeArticle()
        }) {
            Icon(
                imageVector = if (article.isLiked) Icons.Default.ThumbUp else Icons.Outlined.ThumbUp,
                contentDescription = null,
                tint = if(article.isLiked) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.onSurface,
                modifier =
                Modifier
                    .alpha(if (article.isLiked) 1.0f else 0.5f)
                    .size(20.dp)
            )
        }
        IconButton(onClick = {
            if (article.isSaved) {
                unSaveArticle()
            } else {
                saveArticle()
            }
        }) {
            Icon(
                imageVector = if (article.isSaved) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                contentDescription = null,
                Modifier
                    .alpha(if (article.isSaved) 1f else 0.5f)
                    .size(20.dp)
            )
        }
        IconButton(onClick = {
            shareArticle(context, article)
        }) {
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = null,
                Modifier
                    .alpha(0.5f)
                    .size(20.dp)
            )
        }

    }
}

fun shareArticle(context: Context, article: Article) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TITLE, article.title)
        putExtra(Intent.EXTRA_TEXT, article.url)
        type = "text/plain"
    }

    val shareIntent = Intent.createChooser(sendIntent, null)
    context.startActivity(shareIntent)
}