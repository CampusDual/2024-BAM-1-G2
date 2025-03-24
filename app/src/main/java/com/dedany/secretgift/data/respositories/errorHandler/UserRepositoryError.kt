package com.dedany.secretgift.domain.errors

sealed class UserRepositoryError(message: String) : Exception(message) {
    class UserNotFoundError(message: String) : UserRepositoryError(message)
    class NetworkError(message: String) : UserRepositoryError(message)
    class UnexpectedError(message: String) : UserRepositoryError(message)
}