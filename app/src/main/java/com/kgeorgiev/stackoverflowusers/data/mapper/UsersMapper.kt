package com.kgeorgiev.stackoverflowusers.data.mapper

import com.kgeorgiev.stackoverflowusers.data.network.responses.UserDto
import com.kgeorgiev.stackoverflowusers.data.storage.entity.FollowedUsersEntity
import com.kgeorgiev.stackoverflowusers.domain.model.User

fun UserDto.toDomain(isFollowed: Boolean): User {
    return User(
        accountId = accountId,
        displayName = displayName,
        reputation = reputation,
        profileImageUrl = profileImageUrl,
        isFollowed = isFollowed
    )
}

fun User.toEntity(): FollowedUsersEntity {
    return FollowedUsersEntity(accountId = accountId)
}