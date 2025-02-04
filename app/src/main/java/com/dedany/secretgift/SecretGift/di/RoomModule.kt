package com.dedany.secretgift.SecretGift.di

import android.app.Application
import com.dedany.secretgift.SecretGift.data.dataSources.RoomDb
import com.example.baseproject.data.dataSources.games.local.GamesDao

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


// AQUI SE EJECUTA ROOM , MEDIANTE HILT
@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideLocalDataSource(application: Application): RoomDb {
        return RoomDb.invoke(application)

    }

    @Provides
    @Singleton
    fun provideGamesDao( database: RoomDb): GamesDao {
        return database.gamesDao()
    }

}