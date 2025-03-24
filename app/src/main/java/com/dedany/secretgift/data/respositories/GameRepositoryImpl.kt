package com.dedany.secretgift.data.respositories

import android.database.sqlite.SQLiteException
import android.util.Log
import com.dedany.secretgift.data.dataSources.games.local.LocalDataSource
import com.dedany.secretgift.data.dataSources.games.local.gameDbo.GameDbo
import com.dedany.secretgift.data.dataSources.games.local.gameDbo.PlayerDbo
import com.dedany.secretgift.data.dataSources.games.local.gameDbo.RuleDbo
import com.dedany.secretgift.data.dataSources.games.remote.GameRemoteDataSource
import com.dedany.secretgift.data.dataSources.games.remote.dto.CreateGameDto
import com.dedany.secretgift.data.dataSources.games.remote.dto.CreatePlayerDto
import com.dedany.secretgift.data.dataSources.games.remote.dto.GameDto
import com.dedany.secretgift.data.dataSources.games.remote.dto.GameRuleDto
import com.dedany.secretgift.data.dataSources.games.remote.dto.GameSummaryDto
import com.dedany.secretgift.data.dataSources.games.remote.dto.PlayerDto
import com.dedany.secretgift.data.dataSources.games.remote.dto.SendEmailToPlayerDto
import com.dedany.secretgift.data.dataSources.games.remote.dto.UserRegisteredDto
import com.dedany.secretgift.data.dataSources.users.local.preferences.UserPreferences
import com.dedany.secretgift.data.respositories.errorHandler.GameRepositoryError
import com.dedany.secretgift.domain.entities.CreateGame
import com.dedany.secretgift.domain.entities.CreatePlayer
import com.dedany.secretgift.domain.entities.Game
import com.dedany.secretgift.domain.entities.GameSummary
import com.dedany.secretgift.domain.entities.LocalGame
import com.dedany.secretgift.domain.entities.Player
import com.dedany.secretgift.domain.entities.Rule
import com.dedany.secretgift.domain.entities.User
import com.dedany.secretgift.domain.repositories.GamesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(
    private val remoteDataSource: GameRemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val userPreferences: UserPreferences
) : GamesRepository {

    override suspend fun getGame(gameCode: String): Game {
        return withContext(Dispatchers.IO) {
            try {
                val gameDto = remoteDataSource.getGame(gameCode)
                gameDto.toDomain()
            } catch (e: Exception) {
                throw handleRepositoryError(e)
            }
        }
    }

    override suspend fun getLocalGame(gameId: Int): LocalGame {
        return withContext(Dispatchers.IO) {
            try {
                val gameDbo = localDataSource.getLocalGame(gameId)
                gameDbo.toDomain()
            } catch (e: Exception) {
                throw handleRepositoryError(e)
            }
        }
    }

    override suspend fun getLocalGameById(id: Int): LocalGame {
        return withContext(Dispatchers.IO) {
            try {
                localDataSource.getLocalGameById(id).toDomain()
            } catch (e: Exception) {
                throw handleRepositoryError(e)
            }
        }
    }

    override suspend fun getGamesByUser(): List<GameSummary> {
        val userId = userPreferences.getUserId()
        if (userId.isEmpty()) {
            throw GameRepositoryError.UserNotFoundError()
        }

        return withContext(Dispatchers.IO) {
            try {
                remoteDataSource.getGamesByUser(userId).map { it.toDomain() }
            } catch (e: Exception) {
                throw handleRepositoryError(e)
            }
        }
    }

    override suspend fun getLocalGamesByUser(): List<LocalGame> {
        return withContext(Dispatchers.IO) {
            try {
                val userId = userPreferences.getUserId()
                localDataSource.getLocalGamesByUser(userId).map { it.toDomain() }
            } catch (e: Exception) {
                throw handleRepositoryError(e)
            }
        }
    }

    override suspend fun sendMailToPlayer(gameId: String, playerId: String, playerEmail: String): Boolean {
        val sendEmailToPlayerDto = SendEmailToPlayerDto(gameId, playerId, playerEmail)
        return try {
            remoteDataSource.sendMailToPlayer(sendEmailToPlayerDto)
        } catch (e: Exception) {
            throw handleRepositoryError(e)
        }
    }

    override suspend fun deleteAllGames(): Boolean {
        return try {
            localDataSource.deleteAllGames()
        } catch (e: Exception) {
            throw handleRepositoryError(e)
        }
    }

    override suspend fun deleteLocalGame(gameId: Int): Boolean {
        return try {
            localDataSource.deleteGame(gameId)
        } catch (e: Exception) {
            throw handleRepositoryError(e)
        }
    }

    override suspend fun createLocalGame(game: LocalGame): Long {
        return try {
            localDataSource.createGame(game.toDbo())
        } catch (e: Exception) {
            throw handleRepositoryError(e)
        }
    }

    override suspend fun updateLocalGame(game: LocalGame): Int {
        return try {
            localDataSource.updateGame(game.toDbo())
        } catch (e: Exception) {
            throw handleRepositoryError(e)
        }
    }

    override suspend fun createGame(game: CreateGame): Boolean {
        return try {
            val gameDto = game.toDto()
            remoteDataSource.createGame(gameDto)
        } catch (e: Exception) {
            throw handleRepositoryError(e)
        }
    }

    override suspend fun deleteGame(gameId: String): Boolean {
        return try {
            remoteDataSource.deleteGame(gameId)
        } catch (e: Exception) {
            throw handleRepositoryError(e)
        }
    }

    override suspend fun updateGame(game: Game) {
        // Lógica de actualización si es necesario
    }

    private fun handleRepositoryError(e: Exception): GameRepositoryError {
        return when (e) {
            is SQLiteException -> {
                GameRepositoryError.DatabaseError(e.message ?: "Error en la base de datos")
            }
            is IOException -> {
                GameRepositoryError.NetworkError(e.message ?: "Error de red")
            }
            is GameRepositoryError.UserNotFoundError -> {
                e
            }
            else -> {
                GameRepositoryError.UnexpectedError(e.message ?: "Error inesperado")
            }
        }
    }

    private fun GameDto.toDomain(): Game {
        return Game(
            id = this.id,
            name = this.name,
            ownerId = this.ownerId,
            status = this.status,
            gameCode = this.gameCode,
            maxCost = this.maxCost,
            minCost = this.minCost,
            gameDate = this.gameDate,
            players = this.players.map { it.toDomain() },
            currentPlayer = this.currentPlayer,
            matchedPlayer = this.matchedPlayer,
            rules = this.rules.map { it.toDomain() },
        )
    }


    private fun LocalGame.toDbo(): GameDbo {
        return GameDbo(
            id = this.id,
            name = this.name,
            ownerId = this.ownerId,
            maxCost = this.maxCost,
            minCost = this.minCost,
            gameDate = this.gameDate ?: Date(),
            players = this.players.map { it.toDbo() },
            rules = this.rules.map { it.toDbo() }
        )
    }


    private fun GameDbo.toDomain(): LocalGame {
        return LocalGame(
            id = this.id,
            name = this.name,
            ownerId = this.ownerId,
            maxCost = this.maxCost,
            minCost = this.minCost,
            gameDate = this.gameDate,
            players = this.players.map { it.toDomain() },
            rules = this.rules.map { it.toDomain() }
        )
    }

    private fun UserRegisteredDto.toRegisteredUser(): User {
        return User(
            id = this.userId,
            name = this.name,
            email = this.email,

        )
    }

    private fun Player.toDbo(): PlayerDbo {
        return PlayerDbo(
            name = this.name,
            email = this.email
        )
    }


    private fun PlayerDbo.toDomain(): Player {
        return Player(
            name = this.name,
            email = this.email
        )
    }

    private fun PlayerDto.toDomain(): User {
        return User(
            id = this.id,
            name = this.name,
            email = this.email,
            mailStatus = this.mailStatus
        )
    }


    private fun GameRuleDto.toDomain(): Rule {
        return Rule(
            playerOne = this.playerOne,
            playerTwo = this.playerTwo
        )
    }

    private fun RuleDbo.toDomain(): Rule {
        return Rule(
            playerOne = this.playerOne,
            playerTwo = this.playerTwo
        )
    }

    private fun Rule.toDto(): GameRuleDto {
        return GameRuleDto(
            playerOne = this.playerOne.toString(),
            playerTwo = this.playerTwo.toString()
        )
    }

    private fun Rule.toDbo(): RuleDbo {
        return RuleDbo(
            playerOne = this.playerOne.toString(),
            playerTwo = this.playerTwo.toString()
        )
    }


    private fun CreateGame.toDto(): CreateGameDto {
        return CreateGameDto(

            name = this.name,
            ownerId = this.ownerId,
            status = this.status,
            maxCost = this.maxCost,
            minCost = this.minCost,
            gameDate = this.gameDate,
            players = this.players.map { it.toDto() },
            rules = this.rules.map { it.toDto() },


            )
    }

    private fun CreatePlayer.toDto(): CreatePlayerDto {
        return CreatePlayerDto(
            name = this.name,
            email = this.email,
            linkedTo = this.linkedTo
        )
    }

    private fun String.toDate(): Date? {
        return try {
            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            formatter.parse(this)
        } catch (e: Exception) {
            null
        }
    }

    private fun GameSummaryDto.toDomain(): GameSummary {
        return GameSummary(
            id = this.id,
            name = this.name,
            status = this.status,
            accessCode = this.accessCode,
            date = this.gameDate
        )
    }
}


