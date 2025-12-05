package com.example.api_retro.model

data class ArtistResponse(
    val artists: List<Artist>? // Puede ser nulo si no encuentra nada
)

// En model/ArtistModel.kt
data class Artist(
    val idArtist: String,
    val strArtist: String,
    val strGenre: String? = null, // <--- Agrega "= null" o el valor que corresponda
    val strArtistThumb: String? = null,
    val strBiographyEN: String? = null,
    val intFormedYear: String? = null,
    val strCountry: String? = null,
    val strWebsite: String? = null,
    // ... haz lo mismo con el resto de campos ...
    val strFacebook: String? = null
)
