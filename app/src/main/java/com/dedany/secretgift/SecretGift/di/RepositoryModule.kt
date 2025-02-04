package com.dedany.secretgift.SecretGift.di

import com.dedany.secretgift.SecretGift.data.repositories.GameRepositoryImpl
import com.dedany.secretgift.SecretGift.domain.repositories.GamesRepository
import com.example.baseproject.data.dataSources.games.local.GamesDao
import com.example.baseproject.data.dataSources.games.remote.GameRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideGamesRepository(
        remoteGamesDataSource: GameRemoteDataSource,
        localGameDataSource : GamesDao
    ): GamesRepository {
        return GameRepositoryImpl(
            remoteGamesDataSource,localGameDataSource
        )
    }
}