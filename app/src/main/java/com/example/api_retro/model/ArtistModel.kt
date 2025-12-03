package com.example.api_retro.model

data class ArtistResponse(
    val artists: List<Artist>? // Puede ser nulo si no encuentra nada
)

data class Artist(
    val idArtist: String,       // ID único (ej: "111239")
    val strArtist: String,      // Nombre (ej: "Coldplay")
    val strGenre: String?,      // Género (ej: "Alternative Rock")
    val strStyle: String?,      // Estilo (ej: "Rock/Pop")
    val strArtistThumb: String?,// Imagen cuadrada del artista
    val strBiographyEN: String? // Biografía en inglés
)
