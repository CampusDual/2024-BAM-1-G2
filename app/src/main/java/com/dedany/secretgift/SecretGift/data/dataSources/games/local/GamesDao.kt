package com.example.baseproject.data.dataSources.games.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
@Dao
interface GamesDao {

    @Query("SELECT * FROM games")
    fun getAllGames(): List<GameDbo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllGames(gamesDbo: List<GameDbo>)


}