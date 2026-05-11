package com.kgeorgiev.stackoverflowusers.presentation.users

import com.kgeorgiev.stackoverflowusers.domain.error.AppError
import com.kgeorgiev.stackoverflowusers.domain.model.User

data class UsersScreenState(
    val isLoading: Boolean = false,
    val usersList: List<User> = emptyList(),
    val error: AppError? = null,
    val processingUserIds: Set<Long> = emptySet()
)

sealed interface UsersActions {
    data class FollowUser(val accountId: Long) : UsersActions
    data class UnFollowUser(val accountId: Long) : UsersActions
}

// Events no needed for now