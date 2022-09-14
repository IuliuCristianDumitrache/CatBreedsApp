package com.example.cat.di

import com.example.cat.data.repository.CatRepository
import com.example.cat.data.repository.CatRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class CatModule {
    @Binds
    abstract fun bindCatRepository(catRepositoryImpl: CatRepositoryImpl): CatRepository
}