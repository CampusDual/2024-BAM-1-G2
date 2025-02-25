package com.dedany.secretgift.data.respositories

import com.dedany.secretgift.data.dataSources.auth.remote.AuthRemoteDataSource
import com.dedany.secretgift.data.dataSources.auth.remote.dto.LoginDto
import com.dedany.secretgift.data.dataSources.users.remote.dto.UserDto
import com.dedany.secretgift.domain.entities.RegisteredUser
import com.dedany.secretgift.domain.repositories.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource
) :
    AuthRepository {
    override suspend fun login(email: String, password: String): Boolean {
        val credentials = LoginDto(email, password, null)
        val dto = authRemoteDataSource.login(credentials)
        if (!dto.token.isNullOrEmpty()) {
            //Guardo en local
            return true
        }
        return false
    }

    override fun logout(): Boolean {
        return authRemoteDataSource.logout()
    }





    override suspend fun getUsers(): List<RegisteredUser> {
        TODO("Not yet implemented")
    }
}