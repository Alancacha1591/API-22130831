package com.example.api_retro.model

data class AlbumResponse(
    val album: List<Album>?
)

data class Album(
    val idAlbum: String,
    val strAlbum: String,       // Nombre del disco
    val intYearReleased: String,// Año
    val strAlbumThumb: String?  // Portada del disco
)