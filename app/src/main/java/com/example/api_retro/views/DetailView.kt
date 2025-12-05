package com.example.api_retro.views

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.api_retro.components.AlbumCard
import com.example.api_retro.components.MainImage
import com.example.api_retro.components.MainTopBar
import com.example.api_retro.utils.Constants.Companion.CUSTOM_BLACK
import com.example.api_retro.viewModel.MusicViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailView(viewModel: MusicViewModel, navController: NavController) {
    val state = viewModel.state

    Scaffold(
        topBar = {
            MainTopBar(
                title = state.artistName,
                showBackButton = true, // Activamos el botón
                onClickBackButton = { navController.popBackStack() } // Acción de volver
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color(CUSTOM_BLACK))
                .verticalScroll(rememberScrollState())
        ) {
            // ... (Tu código de imagen y textos se mantiene igual) ...
            MainImage(image = state.artistImage)

            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Biografía", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Text(text = state.biography, color = Color.LightGray)
            }

            Text(text = "Discografía", color = Color.White, fontSize = 20.sp, modifier = Modifier.padding(16.dp))

            // Aquí nos aseguramos de que se muestren todos los disponibles
            if (state.albums.isNotEmpty()) {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    modifier = Modifier.height(180.dp) // Altura fija para evitar recortes
                ) {
                    items(state.albums) { album ->
                        AlbumCard(album)
                    }
                }
            } else {
                Text("No albums found (API Limit)", color = Color.Red, modifier = Modifier.padding(16.dp))
            }
        }
    }
}