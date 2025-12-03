package com.example.api_retro.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.api_retro.components.MainTopBar
import com.example.api_retro.model.Artist
import com.example.api_retro.utils.Constants.Companion.CUSTOM_BLACK
import com.example.api_retro.viewModel.MusicViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(viewModel: MusicViewModel, navController: NavController) {
    Scaffold(
        topBar = {
            // Quitamos el botón de búsqueda de aquí porque ya estará abajo
            MainTopBar(title = "Metal Wiki \uD83E\uDD18", onClickBackButton = {}) {}
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .background(Color(CUSTOM_BLACK))
        ) {
            // --- NUEVO: BARRA DE BÚSQUEDA FLOTANTE ---
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable { navController.navigate("SearchGameView") }, // Al tocar, te lleva a la vista de búsqueda
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = Color.Gray)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = "Buscar banda (ej: Rammstein)...", color = Color.Gray)
                }
            }

            // --- TU LISTA DE FAVORITOS ---
            ContentHomeView(viewModel, PaddingValues(0.dp), navController)
        }
    }
}

@Composable
fun ContentHomeView(viewModel: MusicViewModel, pad: PaddingValues, navController: NavController) {
    // 1. Observamos la lista de artistas (MusicViewModel), no de juegos
    val artists by viewModel.artists.collectAsState()

    LazyColumn(
        modifier = Modifier
            .padding(pad)
            .background(Color(CUSTOM_BLACK))
    ) {
        // 2. Iteramos sobre los artistas
        items(artists) { artist ->
            // 3. Usamos ArtistCard (que tienes definida abajo o en BodyComponents)
            ArtistCard(artist) {
                // Al hacer clic: cargamos detalle y navegamos
                viewModel.getArtistDetail(artist)
                navController.navigate("DetailView")
            }
        }
    }
}

@Composable
fun ArtistCard(artist: Artist, onClick: () -> Unit) {
    Card(
        modifier = Modifier.padding(8.dp).fillMaxWidth().clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black) // Tarjetas negras
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = artist.strArtistThumb,
                contentDescription = null,
                modifier = Modifier.size(100.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = artist.strArtist, fontWeight = FontWeight.Bold, color = Color.White)
                Text(text = artist.strGenre ?: "Metal", color = Color.LightGray)
            }
        }
    }
}