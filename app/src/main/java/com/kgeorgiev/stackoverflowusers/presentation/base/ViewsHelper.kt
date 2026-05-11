package com.kgeorgiev.stackoverflowusers.presentation.base

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.kgeorgiev.stackoverflowusers.R
import com.kgeorgiev.stackoverflowusers.domain.error.AppError

@Composable
fun getErrorText(appError: AppError?): String? {
    return when (appError) {
        AppError.LocalStorage -> stringResource(R.string.error_local_storage)
        AppError.Network -> stringResource(R.string.error_network)
        AppError.Server -> stringResource(R.string.error_occurs_with_server)
        AppError.Unknown -> stringResource(R.string.unknown_error_please_try_again)
        else -> null
    }
}