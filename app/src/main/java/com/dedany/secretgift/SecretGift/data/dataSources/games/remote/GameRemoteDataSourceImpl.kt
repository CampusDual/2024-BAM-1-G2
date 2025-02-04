package com.example.baseproject.data.dataSources.games.remote

import com.example.baseproject.data.dataSources.games.remote.api.SecretGiftApi
import com.example.baseproject.data.dataSources.games.remote.dto.GameDto
import javax.inject.Inject

class GameRemoteDataSourceImpl @Inject constructor(
    private val gamesApi: SecretGiftApi
) : GameRemoteDataSource {

    override suspend fun getGames(): List<GameDto> {
        val games = gamesApi.getGames().body()?.data ?: emptyList()
        return games
    }
}
