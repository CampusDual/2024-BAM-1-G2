package com.dedany.secretgift.data.respositories

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
import com.dedany.secretgift.data.dataSources.games.remote.dto.PlayerDto
import com.dedany.secretgift.data.dataSources.games.remote.dto.SaveGameDto
import com.dedany.secretgift.data.dataSources.games.remote.dto.SavePlayerDto
import com.dedany.secretgift.data.dataSources.games.remote.dto.UserRegisteredDto
import com.dedany.secretgift.domain.entities.CreateGame
import com.dedany.secretgift.domain.entities.CreatePlayer
import com.dedany.secretgift.domain.entities.Game
import com.dedany.secretgift.domain.entities.LocalGame
import com.dedany.secretgift.domain.entities.Player
import com.dedany.secretgift.domain.entities.Rule
import com.dedany.secretgift.domain.entities.SavePlayer
import com.dedany.secretgift.domain.entities.User
import com.dedany.secretgift.domain.repositories.GamesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(
    private val remoteDataSource: GameRemoteDataSource,
    private val localDataSource: LocalDataSource,
) : GamesRepository {

    override suspend fun getGame(gameCode: String): Game {
        return withContext(Dispatchers.IO) {

            try {
                val gameDto = remoteDataSource.getGame(gameCode)
                return@withContext gameDto.toDomain()
            } catch (e: Exception) {
                Log.e("getGame", "Error obteniendo juego: ${e.message}")
                throw e
            }
        }
    }

    override suspend fun getLocalGame(gameId: Int): LocalGame {
        return withContext(Dispatchers.IO) {
            try {
                val gameDbo = localDataSource.getLocalGame(gameId)
                return@withContext gameDbo.toDomain()
            } catch (e: Exception) {
                Log.e("getLocalGame", "Error obteniendo juego de borrador: ${e.message}")
                throw e
            }
        }
    }

    override suspend fun getLocalGameById(id: Int): LocalGame {
        return withContext(Dispatchers.IO) {
            try {
                val gameDbo = localDataSource.getLocalGameById(id) // Método en el LocalDataSource
                if (gameDbo != null) {
                    return@withContext gameDbo.toDomain()
                } else {
                    // Maneja el caso en que no se encontró el juego
                    Log.e("getGameById", "Juego no encontrado con el id: $id")
                    throw Exception("Juego no encontrado con el id: $id")
                }
            } catch (e: Exception) {
                Log.e("getGameById", "Error obteniendo juego por id: ${e.message}")
                throw e
            }
        }
    }



    override suspend fun getGamesByUser(): List<Game> {
        return withContext(Dispatchers.IO) {
            try {
                val gamesDto = remoteDataSource.getGamesByUser()
                return@withContext gamesDto.map { it.toDomain() }
            } catch (e: Exception) {
                Log.e("getGamesByUser", "Error obteniendo juegos: ${e.message}")
                return@withContext emptyList<Game>()
            }
        }
    }

    override suspend fun deleteLocalGame(game: LocalGame) {
        localDataSource.deleteGame(game.toDbo())
    }

    override suspend fun createLocalGame(game: LocalGame): Long {
        return localDataSource.createGame(game.toDbo())
    }

    override suspend fun updateLocalGame(game: LocalGame) : Int{
        return localDataSource.updateGame(game.toDbo())
    }

    override suspend fun createGame(game: CreateGame): Boolean {
        val gameDto = game.toDto()
        return remoteDataSource.createGame(gameDto)
    }

    override suspend fun saveGameToBackend(
        gameId: Int,
        ownerId: String,
        gameName: String,
        players: List<SavePlayer>,
        eventDate: String,
        numPlayers: String,
        maxPrice: String,
        incompatibilities: List<Pair<String, String>>
    ): Boolean {
        return try {
            val gameDto = SaveGameDto(
                name = gameName,
                ownerId = ownerId,
                status = "Active",
                maxCost = maxPrice.toInt(),
                gameDate = eventDate.toDate(),
                players = players.map { it.toDto() },
                rules = incompatibilities.map {
                    GameRuleDto(playerOne = it.first, playerTwo = it.second)
                }
            )
            remoteDataSource.saveGame(gameDto)
            true
        } catch (e: Exception) {
            Log.e("saveGameToBackend", "Error saving game: ${e.message}")
            false
        }
    }



    override suspend fun updateGame(game: Game) {
//        val gameDto = game.toDto()
//        remoteDataSource.updateGame(gameDto)
    }

    override suspend fun deleteGame(game: Game) {
//        val gameDto = game.toDto()
//        remoteDataSource.deleteGame(gameDto)
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
            email = this.email
        )
    }

    private fun Player.toDbo(): PlayerDbo {
        return PlayerDbo(
            name = this.name,
            email = this.email
        )
    }

    private fun Player.toDto(): PlayerDto {
        return PlayerDto(
            name = this.name,
            email = this.email,
        )
    }


    private fun SavePlayer.toDto(): SavePlayerDto {
        return SavePlayerDto(
            name = this.name,
            email = this.email,
            linkedTo = this.linkedTo
        )
    }

    private fun PlayerDbo.toDomain(): Player {
        return Player(
            name = this.name,
            email = this.email
        )
    }

    private fun PlayerDto.toDomain(): Player {
        return Player(
            name = this.name,
            email = this.email
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
            gameDate = this.gameDate,
            players = this.players.map { it.toDto() },
            rules = this.rules.map { it.toDto() },


            )
    }

    private fun CreatePlayer.toDto(): CreatePlayerDto {
        return CreatePlayerDto(
            name = this.name,
            email = this.email,
            linkedTo = null
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


        }


