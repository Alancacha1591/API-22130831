package com.example.api_retro.data

import com.example.api_retro.model.GamesModel
import com.example.api_retro.model.SingleGameModel
import com.example.api_retro.utils.Constants.Companion.API_KEY
import com.example.api_retro.utils.Constants.Companion.ENDPOINT
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface APIGames {
    @GET(ENDPOINT + API_KEY)
    suspend fun getGames(): Response<GamesModel>

    @GET("$ENDPOINT/{id}$API_KEY")
    suspend fun getGameById(@Path(value="id") id: Int):
            Response<SingleGameModel>

}
