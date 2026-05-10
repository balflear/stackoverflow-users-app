package com.kgeorgiev.stackoverflowusers.domain.repository

import com.kgeorgiev.stackoverflowusers.domain.model.User

interface UsersRepository {
    suspend fun getTopUsers(): List<User>

    suspend fun followUser(accountId: Long)

    suspend fun unfollowUser(accountId: Long)
}