package com.dedany.secretgift.data.dataSources.games.remote.dto

import com.google.gson.annotations.SerializedName

data class PlayerDto(
    @SerializedName("user_name") val name: String,
    @SerializedName("user_email") val email: String
)
