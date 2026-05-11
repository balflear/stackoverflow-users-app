package com.kgeorgiev.stackoverflowusers.data.repository

import com.kgeorgiev.stackoverflowusers.data.mapper.toDomain
import com.kgeorgiev.stackoverflowusers.data.network.RequestHelper.request
import com.kgeorgiev.stackoverflowusers.data.network.UsersApi
import com.kgeorgiev.stackoverflowusers.data.storage.FollowedUsersDao
import com.kgeorgiev.stackoverflowusers.data.storage.entity.FollowedUsersEntity
import com.kgeorgiev.stackoverflowusers.domain.model.User
import com.kgeorgiev.stackoverflowusers.domain.repository.UsersRepository
import jakarta.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val usersApi: UsersApi,
    private val followedUsersDao: FollowedUsersDao
) : UsersRepository {
    override suspend fun getTopUsers(): List<User> = request {
        val followedUsersIds = followedUsersDao.getFollowedUserIds()
        usersApi.getTopUsers().items.map { it.toDomain(isFollowed = it.accountId in followedUsersIds) }
    }

    override suspend fun followUser(accountId: Long) {
        followedUsersDao.insertFollowedUser(FollowedUsersEntity(accountId = accountId))
    }

    override suspend fun unfollowUser(accountId: Long) {
        followedUsersDao.deleteFollowedUser(accountId)
    }
}