package com.example.baseproject.domain.entities

import com.dedany.secretgift.SecretGift.domain.entities.User

// Usuario Invitado
data class GuestUser(
    override val id: String,
    override val name: String,
) : User