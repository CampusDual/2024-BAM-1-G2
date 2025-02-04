package com.dedany.secretgift.SecretGift.domain.useCases.games


import com.dedany.secretgift.SecretGift.domain.entities.Game


interface GamesUseCase {

    suspend fun getGames(): List<Game>
}