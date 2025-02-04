package com.example.baseproject.data.dataSources.games.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.dedany.secretgift.SecretGift.domain.entities.User
import com.example.baseproject.data.converters.UserConverter
import com.example.baseproject.domain.entities.RegisteredUser

@Entity(tableName = "games")
@TypeConverters(UserConverter::class)
data class GameDbo(
    @PrimaryKey val id: String,
    val name: String,
    val ownerId: String,
    val averageCost: Int,
    val status: String,
    val roomCode: String,
    val players: List<User> = emptyList()
)

