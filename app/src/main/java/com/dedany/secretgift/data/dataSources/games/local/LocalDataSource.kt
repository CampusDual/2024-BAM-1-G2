package com.dedany.secretgift.data.dataSources.games.local

import com.dedany.secretgift.data.dataSources.games.local.gameDbo.GameDbo
import com.dedany.secretgift.data.dataSources.games.local.gameDbo.PlayerDbo

interface LocalDataSource {

    suspend fun getGames(): List<GameDbo>
    suspend fun getLocalGame(gameId: Int): GameDbo
    suspend fun getLocalGameById(id: Int): GameDbo?
    suspend fun deleteGame(game: GameDbo)
    suspend fun createGame(game: GameDbo): Long
    suspend fun updateGame(game: GameDbo): Int
    suspend fun getLocalGamesByUser(userId: String): List<GameDbo>
}