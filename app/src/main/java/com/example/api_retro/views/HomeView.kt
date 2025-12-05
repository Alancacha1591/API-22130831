package com.example.api_retro.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement // ⬅️ NUEVO
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer // ⬅️ NUEVO
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height // ⬅️ NUEVO
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size // ⬅️ NUEVO
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons // ⬅️ NUEVO
import androidx.compose.material.icons.filled.StarOutline // ⬅️ NUEVO
import androidx.compose.material3.Button // ⬅️ NUEVO
import androidx.compose.material3.ButtonDefaults // ⬅️ NUEVO
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon // ⬅️ NUEVO
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign // ⬅️ NUEVO
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.api_retro.components.ArtistCard
import com.example.api_retro.components.MainTopBar
import com.example.api_retro.utils.Constants.Companion.CUSTOM_BLACK
import com.example.api_retro.viewModel.MusicViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(viewModel: MusicViewModel, navController: NavController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Usamos el delegado de estado para que currentTitle se actualice
    val currentTitle by viewModel::currentCategoryTitle

    // --- EL COMPONENTE DE MENÚ LATERAL ---
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = Color(CUSTOM_BLACK),
                drawerContentColor = Color.White
            ) {
                // ... Tu contenido del menú ...
                Text("MENU", modifier = Modifier.padding(20.dp), fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Divider(color = Color.Gray, modifier = Modifier.padding(bottom = 10.dp))

                DrawerItem("Mis Favoritos") {
                    viewModel.loadCategory("Favorites")
                    scope.launch { drawerState.close() }
                }
                DrawerItem("Top Thrash Metal") {
                    viewModel.loadCategory("Thrash")
                    scope.launch { drawerState.close() }
                }
                DrawerItem("Top Nu Metal") {
                    viewModel.loadCategory("Nu Metal")
                    scope.launch { drawerState.close() }
                }
                DrawerItem("Top Heavy Metal") {
                    viewModel.loadCategory("Heavy Metal")
                    scope.launch { drawerState.close() }
                }
                DrawerItem("Top Metalcore") {
                    viewModel.loadCategory("Metalcore")
                    scope.launch { drawerState.close() }
                }
                DrawerItem("Top Rock Clásico") {
                    viewModel.loadCategory("Classic Rock")
                    scope.launch { drawerState.close() }
                }
                DrawerItem("Top Hard Rock") {
                    viewModel.loadCategory("Hard rock")
                    scope.launch { drawerState.close() }
                }
                DrawerItem("Top Grunge") {
                    viewModel.loadCategory("Grunge")
                    scope.launch { drawerState.close() }
                }
                DrawerItem("Top Rock Alternativo") {
                    viewModel.loadCategory("Rock Alternativo")
                    scope.launch { drawerState.close() }
                }
            }
        }
    ) {
        // --- CONTENIDO PRINCIPAL (SCAFFOLD) ---
        Scaffold(
            topBar = {
                MainTopBar(
                    title = currentTitle,
                    onClickDrawer = { scope.launch { drawerState.open() } },
                    onClickSearch = { navController.navigate("SearchArtistView") }
                )
            }
        ) { paddingValues ->
            // Se pasa el título actual para la lógica condicional
            ContentHomeView(viewModel, paddingValues, navController, currentTitle)
        }
    }
}

@Composable
fun DrawerItem(text: String, onClick: () -> Unit) {
    Text(
        text = text,
        fontSize = 18.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 15.dp, horizontal = 20.dp)
            .clickable { onClick() },
        color = Color.White
    )
}

// ⬅️ FUNCIÓN MODIFICADA
@Composable
fun ContentHomeView(viewModel: MusicViewModel, pad: PaddingValues, navController: NavController, currentTitle: String) {
    val artists by viewModel.artists.collectAsState()

    Column(
        modifier = Modifier
            .padding(pad)
            .background(Color(CUSTOM_BLACK))
            .fillMaxSize()
    ) {
        // Lógica de Condición para el Mensaje de Vacío
        if (currentTitle == "Mis Favoritos" && artists.isEmpty()) {
            EmptyFavoritesMessage(navController = navController)
        } else {
            // Tu LazyColumn existente para mostrar la lista
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


// ⬅️ NUEVO COMPONENTE: Mensaje de Lista de Favoritos Vacía
@Composable
fun EmptyFavoritesMessage(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 64.dp, start = 32.dp, end = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Icono de estrella
        Icon(
            imageVector = Icons.Filled.StarOutline,
            contentDescription = "No favoritos",
            tint = Color.Gray,
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Título del mensaje
        Text(
            text = "¡Aún no tienes artistas favoritos!",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Cuerpo del mensaje
        Text(
            text = "Busca tus artistas preferidos y toca la estrella para agregarlos a esta lista y verlos aquí.",
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))

        // Botón para ir a buscar
        Button(
            onClick = {
                // Navega a tu pantalla de búsqueda
                navController.navigate("SearchArtistView")
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00A389)) // Color atractivo que contraste con el fondo negro
        ) {
            Text("Buscar Artistas Ahora", color = Color.White)
        }
    }
}