package com.example.api_retro.repository

import com.example.api_retro.data.ApiService
import com.example.api_retro.model.Album
import com.example.api_retro.model.Artist
import com.example.api_retro.model.FavoriteArtist
import com.example.api_retro.model.Track
import com.example.api_retro.room.MusicDatabaseDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MusicRepository @Inject constructor(
    private val apiService: ApiService,
    private val musicDao: MusicDatabaseDao
) {

    suspend fun searchArtist(name: String): List<Artist>? {
        val response = apiService.searchArtist(name)
        if (response.isSuccessful) {
            return response.body()?.artists
        }
        return null
    }

    suspend fun getAlbums(artistId: String): List<Album>? {
        val response = apiService.getAlbums(artistId)
        if (response.isSuccessful) {
            return response.body()?.album
        }
        return null
    }

    suspend fun getTracks(albumId: String): List<Track>? {
        val response = apiService.getTracks(albumId)
        if (response.isSuccessful) {
            return response.body()?.track
        }
        return null
    }

    val favorites: Flow<List<FavoriteArtist>> = musicDao.getFavorites()

    suspend fun addFavorite(artist: FavoriteArtist) {
        musicDao.insertFavorite(artist)
    }

    suspend fun deleteFavorite(artist: FavoriteArtist) {
        musicDao.deleteFavorite(artist)
    }

    suspend fun getFavoriteById(id: String): FavoriteArtist? {
        return musicDao.getFavoriteById(id)
    }
}