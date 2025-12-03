package com.example.api_retro.views

import androidx.compose.foundation.background
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.api_retro.components.MainImage
import com.example.api_retro.components.MainTopBar
import com.example.api_retro.utils.Constants.Companion.CUSTOM_BLACK
import com.example.api_retro.viewModel.MusicViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailView(viewModel: MusicViewModel, navController: NavController) {
    val state = viewModel.state // Accedemos al estado (MusicDetailState)

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color.Black)
                .verticalScroll(rememberScrollState()) // Para poder bajar si la bio es larga
        ) {
            // Imagen Gigante de la banda
            AsyncImage(
                model = state.artistImage,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().height(250.dp),
                contentScale = ContentScale.Crop
            )

            // Nombre y Bio
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = state.artistName, fontSize = 30.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = state.biography, color = Color.LightGray, maxLines = 6) // Bio resumida
            }

            // Sección de Discos
            Text(text = "Discography", color = Color.White, fontSize = 20.sp, modifier = Modifier.padding(16.dp))

            // Lista horizontal de discos
            LazyRow(contentPadding = PaddingValues(horizontal = 16.dp)) {
                items(state.albums) { album ->
                    Column(
                        modifier = Modifier.padding(end = 12.dp).width(120.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImage(
                            model = album.strAlbumThumb,
                            contentDescription = null,
                            modifier = Modifier.size(120.dp).clip(RoundedCornerShape(8.dp))
                        )
                        Text(text = album.strAlbum, color = Color.White, fontSize = 12.sp, maxLines = 1)
                        Text(text = album.intYearReleased, color = Color.Gray, fontSize = 10.sp)
                    }
                }
            }
        }
    }
}