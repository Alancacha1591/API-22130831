package com.example.api_retro.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.api_retro.components.AlbumCard
import com.example.api_retro.components.MainImage
import com.example.api_retro.components.MainTopBar
import com.example.api_retro.viewModel.MusicViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailView(viewModel: MusicViewModel, navController: NavController) {
    val state = viewModel.state

    val favorites by viewModel.favorites.collectAsState()

    val isFavorite = favorites.any { it.idArtist == viewModel.currentArtist?.idArtist }

    val brush = Brush.verticalGradient(listOf(Color(0xFF202020), Color.Black))

    Scaffold(
        topBar = {
            MainTopBar(
                title = state.artistName,
                showBackButton = true,
                onClickBackButton = { navController.popBackStack() }
            )
        },
        floatingActionButton = {
            IconButton(
                onClick = {
                    viewModel.currentArtist?.let { artist ->
                        viewModel.toggleFavorite(artist)
                    }
                },
                modifier = Modifier
                    .padding(16.dp)
                    .size(50.dp)
                    .background(Color.White.copy(alpha = 0.2f), shape = androidx.compose.foundation.shape.CircleShape)
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favorito",
                    tint = if (isFavorite) Color.Red else Color.White,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(brush)
                .verticalScroll(rememberScrollState())
        ) {
            MainImage(image = state.artistImage)
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Biografía", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
                Text(text = state.biography, color = Color.LightGray)
            }
            Text(text = "Discografía", color = Color.White, fontSize = 20.sp, modifier = Modifier.padding(16.dp))
            if (state.albums.isNotEmpty()) {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    modifier = Modifier.height(200.dp)
                ) {
                    items(state.albums) { album ->
                        Box(modifier = Modifier.clickable {
                            viewModel.getAlbumTracks(album)
                            navController.navigate("AlbumDetailView")
                        }) {
                            AlbumCard(album)
                        }
                    }
                }
            } else {
                Text("No albums found (API Limit)", color = Color.Red, modifier = Modifier.padding(16.dp))
            }
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}