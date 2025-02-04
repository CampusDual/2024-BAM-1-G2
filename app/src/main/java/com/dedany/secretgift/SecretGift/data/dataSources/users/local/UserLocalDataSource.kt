package com.dedany.secretgift.SecretGift.data.dataSources.users.local

import com.example.baseproject.data.dataSources.users.local.UserDbo

interface UserLocalDataSource {

    suspend fun getUsers(): List<UserDbo>
}