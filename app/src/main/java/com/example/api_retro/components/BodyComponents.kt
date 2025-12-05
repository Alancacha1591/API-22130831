package com.example.api_retro.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu // Necesario para el Dashboard
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.api_retro.model.Album
import com.example.api_retro.model.Artist
import com.example.api_retro.utils.Constants.Companion.CUSTOM_BLACK

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(
    title: String,
    showBackButton: Boolean = false,
    onClickBackButton: () -> Unit = {},
    onClickDrawer: () -> Unit = {}, // ACCIÓN PARA ABRIR MENÚ
    onClickSearch: () -> Unit = {}  // ACCIÓN PARA IR A BUSCAR
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                color = Color.White,
                fontWeight = FontWeight.ExtraBold
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(CUSTOM_BLACK)
        ),
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = { onClickBackButton() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                }
            } else {
                // AQUÍ ESTÁ LA HAMBURGUESA PARA EL MENÚ
                IconButton(onClick = { onClickDrawer() }) {
                    Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu", tint = Color.White)
                }
            }
        },
        actions = {
            if (!showBackButton) {
                // LUPA PARA IR A LA VISTA DE BÚSQUEDA
                IconButton(onClick = { onClickSearch() }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = Color.White)
                }
            }
        }
    )
}

@Composable
fun ArtistCard(artist: Artist, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(10.dp)
            .shadow(8.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = Color(CUSTOM_BLACK)
        )
    ) {
        Column {
            MainImage(image = artist.strArtistThumb ?: "")

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = artist.strArtist,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Text(
                    text = artist.strGenre ?: "Metal",
                    color = Color.LightGray,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun MainImage(image: String) {
    AsyncImage(
        model = image,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxWidth().height(250.dp)
    )
}

@Composable
fun AlbumCard(album: Album) {
    Column(
        modifier = Modifier.padding(end = 12.dp).width(120.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = album.strAlbumThumb ?: "",
            contentDescription = null,
            modifier = Modifier.size(120.dp).clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
        Text(text = album.strAlbum, color = Color.White, fontSize = 12.sp, maxLines = 1, overflow = TextOverflow.Ellipsis, modifier = Modifier.padding(top = 4.dp))
        Text(text = album.intYearReleased, color = Color.Gray, fontSize = 10.sp)
    }
}

@Composable
fun WebsiteButton(url: String?) {
    if (!url.isNullOrEmpty()) {
        val context = LocalContext.current
        val finalUrl = if (url.startsWith("http")) url else "https://$url"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(finalUrl))
        Button(
            onClick = { context.startActivity(intent) },
            colors = ButtonDefaults.buttonColors(contentColor = Color.White, containerColor = Color.DarkGray),
            modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "Visitar Sitio Web Oficial")
        }
    }
}