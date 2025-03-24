package com.dedany.secretgift.data.dataSources.games.remote

import com.dedany.secretgift.data.dataSources.errorHandler.ErrorDto
import com.dedany.secretgift.data.dataSources.games.remote.api.SecretGiftApi
import com.dedany.secretgift.data.dataSources.games.remote.dto.CreateGameDto
import com.dedany.secretgift.data.dataSources.games.remote.dto.GameDto
import com.dedany.secretgift.data.dataSources.games.remote.dto.GameSummaryDto
import com.dedany.secretgift.data.dataSources.games.remote.dto.SendEmailToPlayerDto
import com.dedany.secretgift.data.dataSources.users.local.preferences.UserPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import java.lang.reflect.Type
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class GameRemoteDataSourceImpl @Inject constructor(
    private val gamesApi: SecretGiftApi,
    private val userPreferences: UserPreferences
):GameRemoteDataSource {
    override suspend fun getGame(gameCode: String): GameDto {
        val response = try {
            gamesApi.getGameByAccessCode(gameCode)
        } catch (e: Exception) {
            throw handleNetworkError(e)
        }
        return if (response.isSuccessful) {
            response.body() ?: throw NetworkErrorDto.UnknownErrorDto
        } else {
            throw handleError(response.errorBody())
        }

    }


    override suspend fun getOwnedGamesByUser(): List<GameSummaryDto> {

        val userId = userPreferences.getUserId()

        if (userId.isEmpty()) {
            throw Exception("User ID not found in preferences")


    override suspend fun createGame(game: CreateGameDto): Boolean {
        val response = try {
            gamesApi.createGame(game)
        } catch (e: Exception) {
            throw handleNetworkError(e)
        }
        return response.isSuccessful
    }

    override suspend fun deleteGame(gameId: String, userId: String): Boolean {
        val response = gamesApi.deleteGame(gameId, userId)
        if (response.isSuccessful) {
            return true
        } else {
            return false
//            throw Exception("Error eliminando el juego: ${response.errorBody()?.string()}")
        }
    }

    override suspend fun updateGame(game: GameDto) {
        TODO("Not yet implemented")
    }

    override suspend fun sendMailToPlayer(sendEmailToPlayerDto: SendEmailToPlayerDto): Boolean {
        val response = try {
            gamesApi.sendMailToPlayer(sendEmailToPlayerDto)
        } catch (e: Exception) {
            throw handleNetworkError(e)
        }
        return response.isSuccessful
    }

    override suspend fun getPlayedGamesByUser(): List<GameSummaryDto> {
        val userId = userPreferences.getUserId()

        if (userId.isEmpty()) {
            throw Exception("User ID not found in preferences")
        }
        val response = gamesApi.getPlayedGamesByUser(userId)

        if (response.isSuccessful) {
            return response.body()?.data ?: emptyList()
        } else {
            throw Exception("Error fetching games played by user: ${response.errorBody()?.string()}")
        }
    }


}
    private fun handleNetworkError(e: Exception): NetworkErrorDto {
        return when (e) {
            is UnknownHostException -> NetworkErrorDto.NoInternetConnection
            is SocketTimeoutException -> NetworkErrorDto.TimeOutError
            else -> NetworkErrorDto.UnknownErrorDto
        }
    }

    private fun handleError(errorBody: ResponseBody?): NetworkErrorDto {
        return try {
            val type: Type = object : TypeToken<ErrorDto>() {}.type
            val errorDto: ErrorDto = Gson().fromJson(errorBody?.string(), type)
            NetworkErrorDto.FailureError(400, errorDto.message ?: "Unknown error")
        } catch (e: Exception) {
            NetworkErrorDto.UnknownErrorDto
        }
    }
}
