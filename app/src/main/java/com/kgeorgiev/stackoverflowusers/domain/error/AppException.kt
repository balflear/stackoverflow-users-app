package com.kgeorgiev.stackoverflowusers.domain.error

class AppException(
    val error: AppError,
    cause: Throwable? = null
) : Exception(cause)