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
        // Load users
        getTopUsers()
    }

    override suspend fun handleActions(action: UsersActions) {
        when (action) {
            is UsersActions.FollowUser -> followOrUnfollowUser(action.accountId, followUser = true)
            is UsersActions.UnFollowUser -> followOrUnfollowUser(
                action.accountId,
                followUser = false
            )
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

    private suspend fun followOrUnfollowUser(accountId: Long, followUser: Boolean) {
        updateState { copy(processingUserIds = processingUserIds + accountId, error = null) }

        try {
            if (followUser) {
                followUserUseCase(accountId)
            } else {
                unFollowUserUseCase(accountId)
            }

            // Update isFollowed for specific user
            val updatedUsers = state.value.usersList.map { user ->
                if (user.accountId == accountId) {
                    user.copy(isFollowed = followUser)
                } else {
                    user
                }
            }

            updateState { copy(usersList = updatedUsers) }
        } catch (appException: AppException) {
            updateState { copy(error = appException.error) }
        } finally {
            updateState { copy(processingUserIds = processingUserIds - accountId) }
        }
    }
}