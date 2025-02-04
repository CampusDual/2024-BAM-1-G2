package com.dedany.secretgift.SecretGift.domain.useCases.games

import com.dedany.secretgift.SecretGift.domain.entities.Game
import com.dedany.secretgift.SecretGift.domain.repositories.GamesRepository

import javax.inject.Inject

class GamesUseCaseImpl @Inject constructor(
    private val repository: GamesRepository,
): GamesUseCase {
    override suspend fun getGames(): List<Game> {
        val games = repository.getGames()
        return games
    }
}