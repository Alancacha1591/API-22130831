package com.example.api_retro.repository

import com.example.api_retro.data.ApiService
import com.example.api_retro.model.Artist
import com.example.api_retro.model.Album
import com.example.api_retro.model.Track
import javax.inject.Inject

class MusicRepository @Inject constructor(private val apiService: ApiService) {

    // Buscar Artista (Para el Home o Búsqueda)
    suspend fun searchArtist(name: String): List<Artist>? {
        val response = apiService.searchArtist(name)
        if (response.isSuccessful) {
            return response.body()?.artists
        }
        return null
    }

    // Obtener Álbumes (Para el Detalle)
    suspend fun getAlbums(artistId: String): List<Album>? {
        val response = apiService.getAlbums(artistId)
        if (response.isSuccessful) {
            return response.body()?.album
        }
        return null
    }

    // Obtener Tracks de un Álbum
    suspend fun getTracks(albumId: String): List<Track>? {
        val response = apiService.getTracks(albumId)
        if (response.isSuccessful) {
            return response.body()?.track
        }
        return null
    }
}