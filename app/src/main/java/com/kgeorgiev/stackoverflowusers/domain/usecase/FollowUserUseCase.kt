package com.kgeorgiev.stackoverflowusers.domain.usecase

import com.kgeorgiev.stackoverflowusers.domain.repository.UsersRepository
import jakarta.inject.Inject

class FollowUserUseCase @Inject constructor(private val usersRepository: UsersRepository) {
    suspend operator fun invoke(accountId: Long) {
        usersRepository.followUser(accountId)
    }
}