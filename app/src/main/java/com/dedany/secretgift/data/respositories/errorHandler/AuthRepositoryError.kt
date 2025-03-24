package com.dedany.secretgift.domain.errors

sealed class AuthRepositoryError(message: String) : Exception(message) {
    class InvalidCredentialsError(message: String) : AuthRepositoryError(message)
    class NetworkError(message: String) : AuthRepositoryError(message)
    class UserNotFoundError(message: String) : AuthRepositoryError(message)
    class UnexpectedError(message: String) : AuthRepositoryError(message)
}
