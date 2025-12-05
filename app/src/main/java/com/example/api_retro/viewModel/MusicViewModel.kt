package com.example.api_retro.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api_retro.model.Album
import com.example.api_retro.model.Artist
import com.example.api_retro.model.Track
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

    private val _artists = MutableStateFlow<List<Artist>>(emptyList())
    val artists = _artists.asStateFlow()

    private val _searchResults = MutableStateFlow<List<Artist>>(emptyList())
    val searchResults = _searchResults.asStateFlow()

    var state by mutableStateOf(MusicDetailState())
        private set

    var selectedAlbum by mutableStateOf<Album?>(null)
        private set
    var albumTracks by mutableStateOf<List<Track>>(emptyList())
        private set

    var currentCategoryTitle by mutableStateOf("Mis Favoritos")
        private set

    init {
        loadCategory("Favorites")
    }

    fun loadCategory(category: String) {
        val bandsToLoad = when(category) {
            "Thrash" -> listOf("Metallica", "Megadeth", "Slayer", "Anthrax")
            "Nu Metal" -> listOf("Korn", "Slipknot", "Linkin Park", "System of a Down")
            "Heavy Metal" -> listOf("Iron Maiden", "Judas Priest", "Helloween", "Ozzy Osbourne")
            "Metalcore" -> listOf("Bullet for My Valentine", "Killswitch Engage", "Bring Me the Horizon", "Architects")
            "Classic Rock" -> listOf("Queen", "The Beatles", "Led Zeppelin", "Pink Floyd")
            "Hard rock" -> listOf("Guns N' Roses", "Van Halen", "AC/DC", "Def Leppard")
            "Grunge" -> listOf("Alice in Chains", "Nirvana", "Soundgarden", "Pearl Jam")
            "Rock Alternativo" -> listOf("The Killers", "The Strokes", "The White Stripes", "Arctic Monkeys")
            "Favorites" -> listOf("Death", "Poppy", "Avenged Sevenfold", "Spiritbox", "The Warning")
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

    fun getArtistDetail(artist: Artist) {
        viewModelScope.launch {
            state = state.copy(
                artistName = artist.strArtist,
                biography = artist.strBiographyEN ?: "No biography available",
                genre = artist.strGenre ?: "Music",
                artistImage = artist.strArtistThumb ?: "",
                albums = emptyList() // Limpiamos primero
            )
            withContext(Dispatchers.IO) {
                val albumsResult = repo.getAlbums(artist.idArtist)
                // SIMPLIFICACIÓN: Solo ordenamos por año (el más nuevo primero) y listo.
                val sortedAlbums = albumsResult?.sortedByDescending { it.intYearReleased } ?: emptyList()
                state = state.copy(albums = sortedAlbums)
            }
        }
    }

    fun getAlbumTracks(album: Album) {
        selectedAlbum = album
        albumTracks = emptyList()
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val result = repo.getTracks(album.idAlbum)
                albumTracks = result ?: emptyList()
            }
        }
    }
}