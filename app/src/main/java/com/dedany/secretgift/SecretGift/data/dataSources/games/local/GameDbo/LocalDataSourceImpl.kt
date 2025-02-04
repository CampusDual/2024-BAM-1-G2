package com.example.baseproject.data.dataSources.games.local
import com.example.baseproject.data.dataSources.games.local.GameDbo
import com.example.baseproject.data.dataSources.games.local.GamesDao
import com.example.baseproject.data.dataSources.games.local.LocalDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val gamesDao: GamesDao
) : LocalDataSource {

    override suspend fun getGames(): List<GameDbo> {
        return gamesDao.getAllGames()
    }

    override suspend fun insertGames(games: List<GameDbo>) {
        TODO("Not yet implemented")
    }

    override suspend fun getGameById(id: String): GameDbo? {
        TODO("Not yet implemented")
    }


}