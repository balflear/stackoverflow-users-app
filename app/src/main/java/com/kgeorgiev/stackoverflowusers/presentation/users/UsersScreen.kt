package com.kgeorgiev.stackoverflowusers.presentation.users

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle


@Composable
fun UsersScreen(viewModel: UsersScreenViewModel = hiltViewModel(), paddingValues: PaddingValues) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    UsersScreenContent(onAction = {
        viewModel.submitAction(it)
    }, state, paddingValues)
}