package com.dedany.secretgift.SecretGift.di

import com.dedany.secretgift.SecretGift.domain.repositories.GamesRepository
import com.dedany.secretgift.SecretGift.domain.useCases.games.GamesUseCase
import com.dedany.secretgift.SecretGift.domain.useCases.games.GamesUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGamesUseCase(
        repository: GamesRepository
    ): GamesUseCase {
        return GamesUseCaseImpl(
            repository =  repository
        )
    }

}