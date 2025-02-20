package com.dedany.secretgift.domain.useCases.games





interface GamesUseCase {

    suspend fun getGames(): List<com.dedany.secretgift.domain.entities.Game>
}