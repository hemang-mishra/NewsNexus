package com.project.newsnexus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.project.newsnexus.data.remote.NewsAPI
import com.project.newsnexus.ui.mainscreen.screens.MainScreen
import com.project.newsnexus.ui.mainscreen.viemodel.MainViewModel
import com.project.newsnexus.ui.theme.NewsNexusTheme
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : ComponentActivity() {

    val api = get<NewsAPI>()
    val api2 by inject<NewsAPI>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsNexusTheme {
                //Using viewModel in compose projects
                val viewModel = getViewModel<MainViewModel>()
                viewModel.printing()
//                PrimaryScreen(modifier = Modifier)
                Box(modifier = Modifier.fillMaxSize()) {
                    MainScreen(viewModel = viewModel)
                }

            }
        }
    }
}
