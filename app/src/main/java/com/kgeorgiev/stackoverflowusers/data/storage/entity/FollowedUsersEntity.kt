package com.kgeorgiev.stackoverflowusers.data.storage.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "followed_users")
data class FollowedUsersEntity(
    @PrimaryKey
    val accountId: Long
)