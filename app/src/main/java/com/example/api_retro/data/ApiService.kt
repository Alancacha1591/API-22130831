package com.example.api_retro.data

import com.example.api_retro.model.AlbumResponse
import com.example.api_retro.model.ArtistResponse
import com.example.api_retro.model.TrackResponse
import com.example.api_retro.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(Constants.SEARCH_ARTIST)
    suspend fun searchArtist(@Query("s") query: String): Response<ArtistResponse>

    @GET(Constants.ALBUMS_BY_ARTIST)
    suspend fun getAlbums(@Query("i") artistId: String): Response<AlbumResponse>

    @GET("track.php")
    suspend fun getTracks(@Query("m") albumId: String): Response<TrackResponse>
}