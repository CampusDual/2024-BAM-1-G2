package com.example.baseproject.data.dataSources.games.local


import com.example.baseproject.data.dataSources.games.local.GameDbo
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun insertGames(games: List<GameDbo>)
    suspend fun getGames(): List<GameDbo>
    suspend fun getGameById(id: String): GameDbo?
}