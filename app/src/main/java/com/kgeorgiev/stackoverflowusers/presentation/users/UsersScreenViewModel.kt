package com.kgeorgiev.stackoverflowusers.presentation.users

import androidx.lifecycle.viewModelScope
import com.kgeorgiev.stackoverflowusers.domain.error.AppException
import com.kgeorgiev.stackoverflowusers.domain.usecase.FollowUserUseCase
import com.kgeorgiev.stackoverflowusers.domain.usecase.GetTopUsersUseCase
import com.kgeorgiev.stackoverflowusers.domain.usecase.UnFollowUserUseCase
import com.kgeorgiev.stackoverflowusers.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersScreenViewModel @Inject constructor(
    private val getTopUsersUseCase: GetTopUsersUseCase,
    private val followUserUseCase: FollowUserUseCase,
    private val unFollowUserUseCase: UnFollowUserUseCase
) :
    BaseViewModel<UsersScreenState, UsersActions, Unit>(UsersScreenState()) {

    init {
        getTopUsers()
    }

    override suspend fun handleActions(action: UsersActions) {
        when (action) {
            is UsersActions.FollowUser -> followUser(action.accountId)
            is UsersActions.UnFollowUser -> unFollowUser(action.accountId)
        }
    }

    private fun getTopUsers() {
        viewModelScope.launch {
            updateState { copy(isLoading = true) }
            try {
                val topUsers = getTopUsersUseCase()
                updateState { copy(usersList = topUsers) }
            } catch (appException: AppException) {
                updateState { copy(usersList = emptyList(), error = appException.error) }
            } finally {
                updateState { copy(isLoading = false) }
            }
        }
    }

    private suspend fun followUser(accountId: Long) {

        updateState { copy(processingUserIds = processingUserIds + accountId) }
        followUserUseCase(accountId)

        // Update isFollowed for specific user
        val updatedUsers = state.value.usersList.map { user ->
            if (user.accountId == accountId) {
                user.copy(isFollowed = true)
            } else {
                user
            }
        }

        updateState {
            copy(
                usersList = updatedUsers,
                processingUserIds = processingUserIds - accountId
            )
        }
    }

    private suspend fun unFollowUser(accountId: Long) {
        updateState { copy(processingUserIds = processingUserIds + accountId) }
        unFollowUserUseCase(accountId)

        // Update isFollowed for specific user
        val updatedUsers = state.value.usersList.map { user ->
            if (user.accountId == accountId) {
                user.copy(isFollowed = false)
            } else {
                user
            }
        }

        updateState {
            copy(
                usersList = updatedUsers,
                processingUserIds = processingUserIds - accountId
            )
        }
    }
}