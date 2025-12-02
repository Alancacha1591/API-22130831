package com.example.api_retro.repository

import com.example.api_retro.data.APIGames
import com.example.api_retro.model.GameList
import com.example.api_retro.model.SingleGameModel
import javax.inject.Inject

class GamesRepository @Inject constructor(private val apiGames: APIGames) {
    suspend fun getGames(): List<GameList>? {
        val response = apiGames.getGames()
        if (response.isSuccessful){
            return response.body()?.results
        }
        return null
    }
    suspend fun getGameById(id: Int): SingleGameModel? {
        val response = apiGames.getGameById(id)
        if(response.isSuccessful){
            return response.body()
        }
        return null
    }

}