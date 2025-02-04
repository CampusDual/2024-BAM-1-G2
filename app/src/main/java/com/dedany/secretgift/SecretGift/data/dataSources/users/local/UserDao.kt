package com.example.baseproject.data.dataSources.users.local

import androidx.room.Dao
import androidx.room.Query
import com.example.baseproject.data.dataSources.games.local.GameDbo

@Dao
interface UserDao {

    @Query("SELECT * FROM users")
    fun getAllUsers(): List<UserDbo>
}