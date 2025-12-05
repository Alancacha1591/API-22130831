package com.example.api_retro.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.api_retro.model.FavoriteArtist
import kotlinx.coroutines.flow.Flow

@Dao
interface MusicDatabaseDao {

    // Obtener todos los favoritos (Reactivo con Flow)
    @Query("SELECT * FROM favorites_table")
    fun getFavorites(): Flow<List<FavoriteArtist>>

    // Obtener uno solo por ID (para verificar si ya es favorito)
    @Query("SELECT * FROM favorites_table WHERE id_artist = :id")
    suspend fun getFavoriteById(id: String): FavoriteArtist?

    // Insertar (Si ya existe, lo reemplaza)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(artist: FavoriteArtist)

    // Borrar
    @Delete
    suspend fun deleteFavorite(artist: FavoriteArtist)
}