package com.kgeorgiev.stackoverflowusers.data.network.responses

import com.google.gson.annotations.SerializedName

data class UsersResponse(
    val items: List<UserDto>,
    val hasMore: Boolean,
    val quotaMax: Int,
    val quotaRemaining: Int
)

data class UserDto(
    @SerializedName("account_id")
    val accountId: Long,

    @SerializedName("user_id")
    val userId: Long,

    val reputation: Int,

    @SerializedName("profile_image")
    val profileImageUrl: String,

    @SerializedName("display_name")
    val displayName: String
)