package com.kgeorgiev.stackoverflowusers.domain.error

sealed interface AppError {
    data object Server : AppError
    data object Network : AppError
    data object Unknown : AppError
    data object LocalStorage : AppError
}