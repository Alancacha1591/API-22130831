package com.example.api_retro.model

data class TrackResponse(
    val track: List<Track>?
)

data class Track(
    val idTrack: String,
    val strTrack: String,
    val intDuration: String?,
    val intTrackNumber: String?
)