package com.example.baseproject.data.dataSources.games.remote

import com.example.baseproject.data.dataSources.games.remote.dto.GameDto

interface GameRemoteDataSource {
    suspend fun getGames(): List<GameDto>
}