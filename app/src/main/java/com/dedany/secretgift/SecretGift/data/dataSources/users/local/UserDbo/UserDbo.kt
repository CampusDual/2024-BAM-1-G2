package com.example.baseproject.data.dataSources.users.local
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

// Entidad para la base de datos, con Room
@Entity(tableName = "users") // Usamos conversores para convertir los tipos complejos (como List)
data class UserDbo(
    @PrimaryKey val id: String,
    val name: String,
    val email: String? = null,
    val password: String? = null,
    val playerCode: String? = null,
    val linkedTo: String? = null,
    val gameCodes: List<String> = emptyList()
)