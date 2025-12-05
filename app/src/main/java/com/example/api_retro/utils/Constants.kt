package com.example.api_retro.utils

class Constants {

    companion object{
        // La URL base para la versión 1 de la API, usando la key de prueba "2"
        const val BASE_URL = "https://www.theaudiodb.com/api/v1/json/2/"

        // Endpoints específicos que usaremos (se concatenan en la interfaz)
        const val SEARCH_ARTIST = "search.php"
        const val ALBUMS_BY_ARTIST = "album.php"
        const val CUSTOM_BLACK = 0xFF2B2626
        const val CUSTOM_GREEN = 0xFF209B14
    }

}