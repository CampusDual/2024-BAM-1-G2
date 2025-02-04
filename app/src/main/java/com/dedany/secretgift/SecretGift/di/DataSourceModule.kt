package com.dedany.secretgift.SecretGift.di

import com.example.baseproject.data.dataSources.games.remote.GameRemoteDataSourceImpl
import com.example.baseproject.data.dataSources.games.remote.GameRemoteDataSource
import com.example.baseproject.data.dataSources.games.remote.api.SecretGiftApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideGameRemoteDataSource(secretGiftApi: SecretGiftApi): GameRemoteDataSource {
        return GameRemoteDataSourceImpl(secretGiftApi)
    }

}
