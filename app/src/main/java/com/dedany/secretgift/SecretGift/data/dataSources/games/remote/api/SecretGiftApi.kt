package com.example.baseproject.data.dataSources.games.remote.api

import com.example.baseproject.data.dataSources.games.remote.dto.GamesDataDto
import retrofit2.Response
import retrofit2.http.GET

interface SecretGiftApi {

     @GET("/game")
     suspend fun getGames(): Response<GamesDataDto>
}