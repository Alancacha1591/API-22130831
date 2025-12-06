package com.example.api_retro.state

import com.example.api_retro.model.Album

data class MusicDetailState(
    val artistName: String = "",
    val biography: String = "",
    val genre: String = "",
    val artistImage: String = "",
    val albums: List<Album> = emptyList()
)