package com.example.api_retro.model

data class TrackResponse(
    val track: List<Track>?
)

data class Track(
    val idTrack: String,
    val strTrack: String,       // Nombre de la canción
    val intDuration: String?,   // Duración en ms
    val intTrackNumber: String? // Número de pista
)