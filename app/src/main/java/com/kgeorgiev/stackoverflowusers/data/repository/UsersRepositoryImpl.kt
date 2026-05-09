package com.kgeorgiev.stackoverflowusers.data.repository

import com.kgeorgiev.stackoverflowusers.data.mapper.toDomain
import com.kgeorgiev.stackoverflowusers.data.network.UsersApi
import com.kgeorgiev.stackoverflowusers.domain.model.User
import com.kgeorgiev.stackoverflowusers.domain.repository.UsersRepository
import jakarta.inject.Inject

class UsersRepositoryImpl @Inject constructor(private val usersApi: UsersApi) : UsersRepository {
    override suspend fun getTopUsers(): List<User> {
        //TODO: Fetch is followed from local source
        return usersApi.getTopUsers().items.map { it.toDomain() }
    }
}