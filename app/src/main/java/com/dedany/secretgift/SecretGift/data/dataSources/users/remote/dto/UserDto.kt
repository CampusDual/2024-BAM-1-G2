package com.dedany.secretgift.SecretGift.data.dataSources.users.remote.dto

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("_id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String? = null,
    @SerializedName("password") val password: String? = null,
    @SerializedName("playerCode") val playerCode: String? = null,
    @SerializedName("linkedTo") val linkedTo: String? = null,
    @SerializedName("gameCodes") val gameCodes: List<String> = emptyList()
)
