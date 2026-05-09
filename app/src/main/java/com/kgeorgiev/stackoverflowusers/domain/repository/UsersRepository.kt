package com.kgeorgiev.stackoverflowusers.domain.repository

import com.kgeorgiev.stackoverflowusers.domain.model.User

interface UsersRepository {
    suspend fun getTopUsers() : List<User>
}