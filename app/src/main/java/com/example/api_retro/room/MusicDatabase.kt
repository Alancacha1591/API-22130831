package com.example.api_retro.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.api_retro.model.FavoriteArtist

@Database(entities = [FavoriteArtist::class], version = 1, exportSchema = false)
abstract class MusicDatabase : RoomDatabase() {
    abstract fun musicDao(): MusicDatabaseDao
}