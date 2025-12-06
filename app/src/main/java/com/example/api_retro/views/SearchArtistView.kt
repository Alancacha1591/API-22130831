package com.example.api_retro.views

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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
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
fun SearchArtistView(viewModel: MusicViewModel, navController: NavController) {
    var query by remember { mutableStateOf("") }
    val artists by viewModel.searchResults.collectAsState()

    Scaffold(
        containerColor = Color(CUSTOM_BLACK)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(bottom = padding.calculateBottomPadding())
        ) {
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                query = query,
                onQueryChange = { query = it },
                onSearch = { viewModel.fetchSearchArtist(query) },
                active = false,
                onActiveChange = {},
                placeholder = { Text("Busca un artista o una banda...") },
                colors = SearchBarDefaults.colors(containerColor = Color.White),
                leadingIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                trailingIcon = {
                    if (query.isNotEmpty()) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Clear",
                            modifier = Modifier.clickable { query = "" }
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            modifier = Modifier.clickable { viewModel.fetchSearchArtist(query) }
                        )
                    }
                }
            ) {}
            if (artists.isNotEmpty()) {
                Text(
                    text = "Resultados:",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(16.dp)
                )
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
    }
}