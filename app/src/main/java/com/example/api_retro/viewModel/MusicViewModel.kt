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
import com.example.api_retro.model.FavoriteArtist
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map

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

    var currentArtist: Artist? = null
        private set

    // Lista de favoritos (observando la BD)
    private val _favorites = MutableStateFlow<List<FavoriteArtist>>(emptyList())
    val favorites = _favorites.asStateFlow()

    init {
        // Iniciamos la recolección de favoritos
        viewModelScope.launch {
            repo.favorites.collect { list ->
                _favorites.value = list
            }
        }
    }


    // Función para guardar un artista como favorito
    fun toggleFavorite(artist: Artist) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val existing = repo.getFavoriteById(artist.idArtist)
                if (existing != null) {
                    // Si ya existe, lo borramos
                    repo.deleteFavorite(existing)
                } else {
                    // Si no existe, lo creamos
                    val newFav = FavoriteArtist(
                        idArtist = artist.idArtist,
                        strArtist = artist.strArtist,
                        strGenre = artist.strGenre,
                        strArtistThumb = artist.strArtistThumb,
                        strBiographyEN = artist.strBiographyEN
                    )
                    repo.addFavorite(newFav)
                }
            }
        }
    }

    // Función para cargar la categoría "Mis Favoritos" DESDE ROOM
    // Modifica tu función loadCategory así:
    fun loadCategory(category: String) {
        currentCategoryTitle = if (category == "Favorites") "Mis Favoritos" else "Top $category"

        if (category == "Favorites") {
            // Cargar desde Room
            viewModelScope.launch {
                repo.favorites.collect { favs ->
                    // Convertimos FavoriteArtist a Artist para que la vista lo entienda
                    val mappedArtists = favs.map { fav ->
                        Artist(
                            idArtist = fav.idArtist,
                            strArtist = fav.strArtist,
                            strGenre = fav.strGenre,
                            strArtistThumb = fav.strArtistThumb,
                            strBiographyEN = fav.strBiographyEN, // Campos vacíos porque no los guardamos en BD
                            intFormedYear = "",
                            strCountry = "",
                            strWebsite = ""
                        )
                    }
                    _artists.value = mappedArtists
                }
            }
        } else {
            // Cargar desde API (Tu lógica original de when)
            val bandsToLoad = when(category) {
                "Thrash" -> listOf("Metallica", "Megadeth", "Slayer", "Anthrax")
                "Nu Metal" -> listOf("Korn", "Slipknot", "Linkin Park", "System of a Down")
                "Heavy Metal" -> listOf("Iron Maiden", "Judas Priest", "Helloween", "Ozzy Osbourne")
                "Metalcore" -> listOf("Bullet for My Valentine", "Killswitch Engage", "Bring Me the Horizon", "Architects")
                "Classic Rock" -> listOf("Queen", "The Beatles", "Led Zeppelin", "Pink Floyd")
                "Hard rock" -> listOf("Guns N' Roses", "Van Halen", "AC/DC", "Def Leppard")
                "Grunge" -> listOf("Alice in Chains", "Nirvana", "Soundgarden", "Pearl Jam")
                "Rock Alternativo" -> listOf("The Killers", "The Strokes", "The White Stripes", "Arctic Monkeys")
                else -> emptyList()
            }
            fetchBandsList(bandsToLoad)
        }
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
        // 1. Limpieza básica: quitamos espacios
        val cleanQuery = query.trim()

        // Si el usuario borra todo, limpiamos la lista de resultados
        if (cleanQuery.isEmpty()) {
            _searchResults.value = emptyList()
            return
        }

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    // INTENTO 1: Búsqueda normal (tal cual lo escribió el usuario)
                    var result = repo.searchArtist(cleanQuery)

                    // INTENTO 2: Estrategia "Fuzzy" (Truco del comodín)
                    // Si la búsqueda exacta no trajo nada o la lista está vacía...
                    if (result.isNullOrEmpty()) {
                        // Agregamos "%" al final. En muchas bases de datos SQL (que usa la API por detrás),
                        // esto significa "todo lo que empiece con..."
                        // Ejemplo: "Juan%" encontrará "Juanes", "Juan Gabriel", etc.
                        result = repo.searchArtist("$cleanQuery%")
                    }

                    // Actualizamos la lista con lo que hayamos encontrado (o vacía si falló todo)
                    _searchResults.value = result ?: emptyList()

                } catch (e: Exception) {
                    e.printStackTrace()
                    _searchResults.value = emptyList()
                }
            }
        }
    }

    fun getArtistDetail(artist: Artist) {
        currentArtist = artist // <--- GUARDAMOS EL ARTISTA AQUÍ

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