package com.example.api_retro.model

data class AlbumResponse(
    val album: List<Album>?
)

data class Album(
    val idAlbum: String,
    val strAlbum: String,
    val intYearReleased: String,
    val strAlbumThumb: String?,
    val intScore: String? // <--- ESTE FALTABA Y CAUSABA ERROR EN EL ORDENAMIENTO
)