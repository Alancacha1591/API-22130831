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

    @Query("SELECT * FROM favorites_table")
    fun getFavorites(): Flow<List<FavoriteArtist>>

    @Query("SELECT * FROM favorites_table WHERE id_artist = :id")
    suspend fun getFavoriteById(id: String): FavoriteArtist?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(artist: FavoriteArtist)

    @Delete
    suspend fun deleteFavorite(artist: FavoriteArtist)
}