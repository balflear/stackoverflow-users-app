package com.kgeorgiev.stackoverflowusers.domain.usecase

import com.kgeorgiev.stackoverflowusers.domain.model.User
import com.kgeorgiev.stackoverflowusers.domain.repository.UsersRepository
import jakarta.inject.Inject

class GetTopUsersUseCase @Inject constructor(private val usersRepository: UsersRepository) {
    suspend operator fun invoke(): List<User> {
        return usersRepository.getTopUsers()
    }
}