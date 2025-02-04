package com.example.baseproject.data.dataSources.games.remote.dto


import com.dedany.secretgift.SecretGift.data.dataSources.users.remote.dto.UserDto
import com.google.gson.annotations.SerializedName

import java.util.Date

data class GameDto(
    @SerializedName("_id")
    val id: String,

    val name: String,

    @SerializedName("owner_id")
    val ownerId: String,

    @SerializedName("average_cost")
    val averageCost: Int,
    @SerializedName("status")
    val status: String,

    @SerializedName("room_code")
    val roomCode: String,

    @SerializedName("players")
    val players: List<UserDto>

)