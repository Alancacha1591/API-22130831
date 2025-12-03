package com.example.api_retro.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.api_retro.components.ArtistCard
import com.example.api_retro.utils.Constants.Companion.CUSTOM_BLACK
import com.example.api_retro.viewModel.MusicViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchGameView(viewModel: MusicViewModel, navController: NavController) {

    // Variable solo para el texto, ya no necesitamos 'active'
    var query by remember { mutableStateOf("") }

    val artists by viewModel.searchResults.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(Color(CUSTOM_BLACK))
    ) {
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            query = query,
            onQueryChange = { query = it },
            onSearch = {
                viewModel.fetchSearchArtist(query)
                // Ya no necesitamos desactivar nada aquí
            },
            active = false, // TRUCO: Forzamos a que nunca se expanda
            onActiveChange = {  }, // No hacemos nada si intenta activarse
            placeholder = { Text("Buscar banda (ej: Nirvana)...") },
            leadingIcon = {
                // Agregamos botón de regreso para que sea fácil salir
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            trailingIcon = {
                // Mostramos la X solo si hay texto para borrar
                if (query.isNotEmpty()) {
                    Icon(
                        modifier = Modifier.clickable { query = "" },
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close"
                    )
                }
            }
        ) {
            // Contenido vacío porque los resultados están afuera (en el LazyColumn de abajo)
        }

        // Título de resultados (Opcional, se ve bien)
        if (artists.isNotEmpty()) {
            Text(
                text = "Resultados:",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
            )
        }

        // Lista de resultados
        LazyColumn {
            items(artists) { artist ->
                ArtistCard(artist) {
                    viewModel.getArtistDetail(artist)
                    navController.navigate("DetailView")
                }
            }
        }
    }
}