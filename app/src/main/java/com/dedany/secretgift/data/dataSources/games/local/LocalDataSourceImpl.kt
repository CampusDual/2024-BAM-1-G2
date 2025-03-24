package com.dedany.secretgift.data.dataSources.games.local

import com.dedany.secretgift.data.dataSources.games.local.gameDbo.GameDbo
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val gamesDao: GamesDao
) : LocalDataSource {

    override suspend fun getGames(): List<GameDbo> {
        return gamesDao.getGames()
    }

    override suspend fun getLocalGame(gameId: Int): GameDbo {
        return gamesDao.getGame(gameId)
    }

    override suspend fun getLocalGameById(id: Int): GameDbo {
        return gamesDao.getLocalGameById(id)
    }

    override suspend fun deleteGame(gameId: Int): Boolean {
       return gamesDao.deleteGame(gameId)>0
    }

    override suspend fun createGame(game: GameDbo): Long {
        return gamesDao.createLocalGame(game)
    }

    override suspend fun updateGame(game: GameDbo): Int {
       return gamesDao.updateLocalGame(game)
    }

    override suspend fun getLocalGamesByUser(userId: String): List<GameDbo> {
        return gamesDao.getLocalGamesByUser(userId)
    }

    override suspend fun deleteAllGames(): Boolean {
        return try {
            gamesDao.deleteAllGames()
            return true
        } catch (e: Exception) {
            false
        }
    }
}

