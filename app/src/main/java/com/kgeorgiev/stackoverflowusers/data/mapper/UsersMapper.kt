package com.kgeorgiev.stackoverflowusers.data.mapper

import com.kgeorgiev.stackoverflowusers.data.network.responses.UserDto
import com.kgeorgiev.stackoverflowusers.domain.model.User

fun UserDto.toDomain(): User {
    return User(
        accountId = accountId,
        displayName = displayName,
        reputation = reputation,
        profileImageUrl = profileImageUrl,
    )
}
