package com.example.api_retro.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.api_retro.viewModel.MusicViewModel
import com.example.api_retro.views.DetailView
import com.example.api_retro.views.HomeView
import com.example.api_retro.views.SearchGameView

@Composable
fun NavManager(viewModel: MusicViewModel) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "Home") {

        // Vista Principal (Lista de Bandas Favoritas)
        composable("Home") {
            HomeView(viewModel, navController)
        }

        // Vista de Detalle (Bio + Discos)
        composable("DetailView") {
            DetailView(viewModel, navController)
        }

        // Vista de Búsqueda (Para buscar nuevas bandas)
        composable("SearchGameView") {
            SearchGameView(viewModel, navController)
        }
    }
}