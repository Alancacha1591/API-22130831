package com.example.api_retro.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
// ... imports ...
// Asegúrate de recibir MusicViewModel en lugar de GamesViewModel

@Composable
fun HomeView(viewModel: MusicViewModel, navController: NavController) {
    val artists by viewModel.artists.collectAsState() // Observamos la lista

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Metal Wiki 🤘", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black) // Un toque dark
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues).background(Color.DarkGray)
        ) {
            items(artists) { artist ->
                ArtistCard(artist) {
                    // Al dar click, guardamos el estado y navegamos
                    viewModel.getArtistDetail(artist)
                    navController.navigate("DetailView")
                }
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