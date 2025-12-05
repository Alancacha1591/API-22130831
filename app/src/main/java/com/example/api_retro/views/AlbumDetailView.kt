package com.example.api_retro.views

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.api_retro.components.MainTopBar
import com.example.api_retro.viewModel.MusicViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumDetailView(viewModel: MusicViewModel, navController: NavController) {
    val album = viewModel.selectedAlbum
    val tracks = viewModel.albumTracks

    // Gradiente Dark Metal
    val brush = Brush.verticalGradient(
        colors = listOf(Color(0xFF2C0000), Color(0xFF000000)) // Rojo muy oscuro a Negro
    )

    Scaffold(
        topBar = {
            MainTopBar(
                title = album?.strAlbum ?: "Album",
                showBackButton = true,
                onClickBackButton = { navController.popBackStack() } // <--- AHORA SÍ FUNCIONA
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(brush) // Fondo Gradiente
        ) {
            // Cabecera del Álbum (Imagen + Año)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = album?.strAlbumThumb,
                    contentDescription = null,
                    modifier = Modifier
                        .size(140.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(text = album?.strAlbum ?: "", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    Text(text = "Lanzamiento: ${album?.intYearReleased ?: "N/A"}", color = Color.Gray)
                    Text(text = "${tracks.size} Canciones", color = Color(0xFFE91E63), fontWeight = FontWeight.Bold) // Acento Rojo
                }
            }

            Divider(color = Color.DarkGray, thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))

            // Lista de Canciones
            LazyColumn(modifier = Modifier.padding(16.dp)) {
                items(tracks) { track ->
                    TrackItem(track)
                }
            }
        }
    }
}

@Composable
fun TrackItem(track: com.example.api_retro.model.Track) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = track.intTrackNumber ?: "#",
            color = Color.Gray,
            modifier = Modifier.width(30.dp),
            fontWeight = FontWeight.Bold
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(text = track.strTrack, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        }
        if (track.intDuration != "0") {
            // Convertir ms a min:seg (simple)
            val duration = track.intDuration?.toLongOrNull() ?: 0L
            val min = duration / 60000
            Text(text = "${min}m", color = Color.Gray, fontSize = 12.sp)
        }
    }
}