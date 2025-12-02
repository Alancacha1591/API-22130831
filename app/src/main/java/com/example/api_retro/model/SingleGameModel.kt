package com.example.api_retro.model

data class SingleGameModel(
    val name : String,
    val description_raw : String,
    val metacritic: Int,
    val website : String,
    val background_image: String
)