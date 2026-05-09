package com.kgeorgiev.stackoverflowusers.domain.model

data class User(val accountId:Int,
                val displayName:String,
                val reputation:Int,
                val profileImageUrl:String,
                val isFollowed: Boolean = false)
