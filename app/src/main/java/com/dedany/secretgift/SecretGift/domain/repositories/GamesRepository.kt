package com.dedany.secretgift.SecretGift.domain.repositories

import com.dedany.secretgift.SecretGift.domain.entities.Game

interface GamesRepository {

    suspend fun getGames(): List<Game>
}