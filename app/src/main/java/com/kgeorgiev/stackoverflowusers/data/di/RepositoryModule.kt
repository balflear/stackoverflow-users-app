package com.kgeorgiev.stackoverflowusers.data.di

import com.kgeorgiev.stackoverflowusers.data.repository.UsersRepositoryImpl
import com.kgeorgiev.stackoverflowusers.domain.repository.UsersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindUserRepository(usersRepositoryImpl: UsersRepositoryImpl): UsersRepository
}