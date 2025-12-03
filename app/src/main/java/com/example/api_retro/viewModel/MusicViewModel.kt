package com.example.api_retro.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api_retro.model.Artist
import com.example.api_retro.repository.MusicRepository
import com.example.api_retro.state.MusicDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MusicViewModel @Inject constructor(private val repo: MusicRepository) : ViewModel() {

    // Lista del HOME (Favoritos)
    private val _artists = MutableStateFlow<List<Artist>>(emptyList())
    val artists = _artists.asStateFlow()

    // Lista para la BÚSQUEDA
    private val _searchResults = MutableStateFlow<List<Artist>>(emptyList())
    val searchResults = _searchResults.asStateFlow()

    // Estado del DETALLE
    var state by mutableStateOf(MusicDetailState())
        private set

    init {
        fetchFavoriteBands()
    }

    private fun fetchFavoriteBands() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val myFavorites = listOf("Metallica",
                    "Iron Maiden",
                    "Megadeth",
                    "Slayer",
                    "Slipknot",
                    "Black Sabbath",
                    "Judas Priest",
                    "Pantera",
                    "System of a Down",
                    "Rammstein",
                    "Motorhead",
                    "Dio")
                val tempList = mutableListOf<Artist>()
                myFavorites.forEach { bandName ->
                    val result = repo.searchArtist(bandName)
                    result?.firstOrNull()?.let { tempList.add(it) }
                }
                _artists.value = tempList
            }
        }
    }

    // --- NUEVA FUNCIÓN PARA BUSCAR ---
    fun fetchSearchArtist(query: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                // Llamamos a la API con lo que escribió el usuario
                val result = repo.searchArtist(query)
                // Actualizamos la lista de resultados (si es null, ponemos lista vacía)
                _searchResults.value = result ?: emptyList()
            }
        }
    }

    fun getArtistDetail(artist: Artist) {
        viewModelScope.launch {
            state = state.copy(
                artistName = artist.strArtist,
                biography = artist.strBiographyEN ?: "No biography available",
                genre = artist.strGenre ?: "Metal",
                artistImage = artist.strArtistThumb ?: "",
                albums = emptyList()
            )
            val albumsResult = withContext(Dispatchers.IO) {
                repo.getAlbums(artist.idArtist)
            }
            state = state.copy(albums = albumsResult ?: emptyList())
        }
    }
}