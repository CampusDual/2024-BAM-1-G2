package com.example.baseproject.domain.entities

import com.dedany.secretgift.SecretGift.domain.entities.User

data class RegisteredUser(
    override val id: String,
    override val name: String,
    val email: String,
    val password: String,
): User