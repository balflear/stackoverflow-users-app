package com.kgeorgiev.stackoverflowusers.data.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kgeorgiev.stackoverflowusers.data.storage.entity.FollowedUsersEntity

@Dao
interface FollowedUsersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFollowedUser(user: FollowedUsersEntity)

    @Query("DELETE FROM followed_users WHERE accountId = :accountId")
    suspend fun deleteFollowedUser(accountId: Long)

    @Query("SELECT accountId FROM followed_users")
    suspend fun getFollowedUserIds(): List<Long>
}