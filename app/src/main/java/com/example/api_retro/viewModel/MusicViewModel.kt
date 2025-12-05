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

    // Lista principal que se muestra en el Home
    private val _artists = MutableStateFlow<List<Artist>>(emptyList())
    val artists = _artists.asStateFlow()

    // Resultados de búsqueda
    private val _searchResults = MutableStateFlow<List<Artist>>(emptyList())
    val searchResults = _searchResults.asStateFlow()

    // Estado del detalle
    var state by mutableStateOf(MusicDetailState())
        private set

    // Titulo actual del Home (para saber en qué categoría estamos)
    var currentCategoryTitle by mutableStateOf("Mis Favoritos")
        private set

    init {
        loadCategory("Favorites") // Carga inicial
    }

    // --- NUEVO: Función maestra para cambiar de categoría ---
    fun loadCategory(category: String) {
        val bandsToLoad = when(category) {
            "Thrash" -> listOf("Metallica", "Megadeth", "Slayer", "Anthrax", "Sepultura")
            "Nu Metal" -> listOf("Korn", "Slipknot", "Limp Bizkit", "Linkin Park", "System of a Down")
            "Classic Rock" -> listOf("Queen", "The Beatles", "Rolling Stones", "Led Zeppelin", "Pink Floyd")
            "Favorites" -> listOf("Metallica", "Iron Maiden", "Megadeth", "Black Sabbath", "Judas Priest", "Pantera", "Rammstein")
            else -> emptyList()
        }

        currentCategoryTitle = if (category == "Favorites") "Mis Favoritos" else "Top $category"
        fetchBandsList(bandsToLoad)
    }

    private fun fetchBandsList(bands: List<String>) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val tempList = mutableListOf<Artist>()
                bands.forEach { bandName ->
                    val result = repo.searchArtist(bandName)
                    result?.firstOrNull()?.let { tempList.add(it) }
                }
                _artists.value = tempList
            }
        }
    }

    // --- BÚSQUEDA ---
    fun fetchSearchArtist(query: String) {
        val cleanQuery = query.trim()
        if (cleanQuery.isEmpty()) return

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val result = repo.searchArtist(cleanQuery)
                _searchResults.value = result ?: emptyList()
            }
        }
    }

    // --- DETALLE ---
    fun getArtistDetail(artist: Artist) {
        viewModelScope.launch {
            state = state.copy(
                artistName = artist.strArtist,
                biography = artist.strBiographyEN ?: "No biography available",
                genre = artist.strGenre ?: "Music",
                artistImage = artist.strArtistThumb ?: "",
                albums = emptyList()
            )
            withContext(Dispatchers.IO) {
                val albumsResult = repo.getAlbums(artist.idArtist)
                state = state.copy(albums = albumsResult ?: emptyList())
            }
        }
    }
}