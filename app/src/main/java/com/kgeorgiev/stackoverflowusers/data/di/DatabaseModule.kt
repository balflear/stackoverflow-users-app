package com.kgeorgiev.stackoverflowusers.data.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.kgeorgiev.stackoverflowusers.data.storage.AppDatabase
import com.kgeorgiev.stackoverflowusers.data.storage.FollowedUsersDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "stackoverflow_users_db"
        ).setQueryCallback(
            { sqlQuery, bindArgs ->
                Log.d("ROOM-QUERY", "SQL: $sqlQuery\nArgs: $bindArgs")
            },
            Executors.newSingleThreadExecutor()
        ).build()
    }

    @Provides
    @Singleton
    fun provideFollowedUsersDao(database: AppDatabase): FollowedUsersDao {
        return database.followedUsersDao()
    }
}