<<<<<<<< Updated upstream:app/src/main/java/com/dedany/secretgift/data/dataSources/games/users/local/UserDbo/UserDbo.kt
package com.dedany.secretgift.data.dataSources.games.users.local.UserDbo
========
package com.dedany.secretgift.data.dataSources.users.local.UserDbo
>>>>>>>> Stashed changes:app/src/main/java/com/dedany/secretgift/data/dataSources/users/local/UserDbo/UserDbo.kt

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters


@Entity(tableName = "users")
data class UserDbo(
    @PrimaryKey val id: String,
    val name: String,
    val email: String,
    val password: String? = null,
    val playerCode: String? = null,
    val linkedTo: String? = null,
    val gameCodes: List<String> = emptyList()
)
