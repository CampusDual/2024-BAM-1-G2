<<<<<<<< Updated upstream:app/src/main/java/com/dedany/secretgift/data/dataSources/games/users/remote/dto/UserDto.kt
package com.dedany.secretgift.data.dataSources.games.users.remote.dto
========
package com.dedany.secretgift.data.dataSources.users.remote.dto
>>>>>>>> Stashed changes:app/src/main/java/com/dedany/secretgift/data/dataSources/users/remote/dto/UserDto.kt

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("_id") val id: String,
    @SerializedName("_name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String? = null,
    @SerializedName("playerCode") val playerCode: String? = null,
    @SerializedName("linkedTo") val linkedTo: String? = null,
    @SerializedName("gameCodes") val gameCodes: String? = null,
)
