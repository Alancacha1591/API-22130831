package com.example.api_retro.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites_table")
data class FavoriteArtist(
    @PrimaryKey
    @ColumnInfo(name = "id_artist")
    val idArtist: String, // Usamos el ID de la API como PrimaryKey para no repetir

    @ColumnInfo(name = "str_artist")
    val strArtist: String,

    @ColumnInfo(name = "str_genre")
    val strGenre: String?,

    @ColumnInfo(name = "str_artist_thumb")
    val strArtistThumb: String?
)