package com.kgeorgiev.stackoverflowusers.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kgeorgiev.stackoverflowusers.data.storage.entity.FollowedUsersEntity

@Database(entities = [FollowedUsersEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun followedUsersDao(): FollowedUsersDao
}