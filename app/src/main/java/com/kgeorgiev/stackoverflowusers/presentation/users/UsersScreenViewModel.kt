package com.kgeorgiev.stackoverflowusers.presentation.users

import androidx.lifecycle.viewModelScope
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
            is UsersActions.FollowUser -> TODO()
            is UsersActions.UnFollowUser -> TODO()
        }
    }

    private fun getTopUsers() {
        viewModelScope.launch {
            updateState { copy(isLoading = true) }
            val topUsers = getTopUsersUseCase()
            updateState { copy(isLoading = false, usersList = topUsers) }
        }
    }
}