<<<<<<<< Updated upstream:app/src/main/java/com/dedany/secretgift/data/dataSources/games/users/RoomDb.kt
package com.dedany.secretgift.data.dataSources.games.users
========
package com.dedany.secretgift.data.dataSources
>>>>>>>> Stashed changes:app/src/main/java/com/dedany/secretgift/data/dataSources/RoomDb.kt

import android.content.Context
import androidx.room.Database
import androidx.room.Room
<<<<<<<< Updated upstream:app/src/main/java/com/dedany/secretgift/data/dataSources/games/users/RoomDb.kt
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.dedany.secretgift.data.dataSources.games.Converters
========
import androidx.room.RoomDatabase  // Corregido el error de importación
>>>>>>>> Stashed changes:app/src/main/java/com/dedany/secretgift/data/dataSources/RoomDb.kt
import com.dedany.secretgift.data.dataSources.games.local.GameDbo.GameDbo
import com.dedany.secretgift.data.dataSources.games.local.GamesDao


@Database(
    entities = [GameDbo::class],
    version = 1,
    exportSchema = false

) //2.Anotacion de @Database pasando entities en vacio y version en 1
@TypeConverters(Converters::class)
abstract class RoomDb : RoomDatabase() { //1.La clase creada debe ser "abstract"

    abstract fun gamesDao(): GamesDao

    //Crear companion object
    companion object {
        private const val DATABASE_NAME = "SecretGift" //Nombre de nuestra Database

        @Volatile //anotacion Volatile para permitir que nuestra instancia de base de datos esté al alcance de todos los hilos
        private var instance: RoomDb? = null
        private val obj = Any()

        operator fun invoke(context: Context) = instance
            ?: synchronized(obj) {// 5. Funcion invoke que lleva la logica de llamar a crear la base de datos
                instance ?: createDatabase(context).also { db ->
                    instance = db
                } // 5.1 Creamos un metodo "createDatabase"
            }

        //6. Llamamos dentro del metodo createDatabase al Room.databaseBuilder y rellenando los parametros del constructor procederemos a hacer un .build()
        private fun createDatabase(context: Context): RoomDb {
            return Room.databaseBuilder(context, RoomDb::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}