package com.example.api_retro.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.api_retro.viewModel.MusicViewModel
import com.example.api_retro.views.AlbumDetailView
import com.example.api_retro.views.DetailView
import com.example.api_retro.views.HomeView
import com.example.api_retro.views.SearchGameView


@Composable
fun NavManager(viewModel: MusicViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "Home") {

        composable("Home") { HomeView(viewModel, navController) }
        composable("DetailView") { DetailView(viewModel, navController) }
        composable("SearchGameView") { SearchGameView(viewModel, navController) }
        composable("AlbumDetailView") { AlbumDetailView(viewModel, navController) }
    }
}