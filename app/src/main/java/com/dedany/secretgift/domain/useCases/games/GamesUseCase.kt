package com.dedany.secretgift.domain.useCases.games

import com.dedany.secretgift.domain.entities.Game


interface GamesUseCase {

    suspend fun getGames(): List<Game>


}