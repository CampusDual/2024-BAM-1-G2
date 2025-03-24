package com.dedany.secretgift.domain.repositories.errorHandler

sealed class GameRepositoryError(message: String) : Exception(message) {
    class NetworkError(message: String) : GameRepositoryError(message)
    class DatabaseError(message: String) : GameRepositoryError(message)
    class UnexpectedError(message: String) : GameRepositoryError(message)
    class GameNotFoundError(message: String) : GameRepositoryError(message)
    class UserNotFoundError : GameRepositoryError("User ID not found in preferences")
}