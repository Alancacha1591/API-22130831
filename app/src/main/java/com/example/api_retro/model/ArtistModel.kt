package com.example.api_retro.model

data class ArtistResponse(
    val artists: List<Artist>?
)

data class Artist(
    val idArtist: String,
    val strArtist: String,
    val strGenre: String? = null,
    val strArtistThumb: String? = null,
    val strBiographyEN: String? = null,
    val intFormedYear: String? = null,
    val strCountry: String? = null,
    val strWebsite: String? = null,
    val strFacebook: String? = null
)
