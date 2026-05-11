package com.kgeorgiev.stackoverflowusers.data.repository

import androidx.sqlite.SQLiteException
import com.kgeorgiev.stackoverflowusers.UnitTestsHelper.createHttpException
import com.kgeorgiev.stackoverflowusers.UnitTestsHelper.getUserDtos
import com.kgeorgiev.stackoverflowusers.UnitTestsHelper.getUsersList
import com.kgeorgiev.stackoverflowusers.data.mapper.toEntity
import com.kgeorgiev.stackoverflowusers.data.network.UsersApi
import com.kgeorgiev.stackoverflowusers.data.network.responses.UsersResponse
import com.kgeorgiev.stackoverflowusers.data.storage.FollowedUsersDao
import com.kgeorgiev.stackoverflowusers.domain.error.AppError
import com.kgeorgiev.stackoverflowusers.domain.error.AppException
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import junit.framework.TestCase.fail
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class UsersRepositoryImplTest {
    private lateinit var usersApi: UsersApi

    private lateinit var usersDao: FollowedUsersDao

    private lateinit var usersRepository: UsersRepositoryImpl

    @Before
    fun setup() {
        usersApi = mockk(relaxed = true)
        usersDao = mockk(relaxed = true)
        usersRepository = UsersRepositoryImpl(usersApi, usersDao)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun getTopUsersWithOneFollowedSuccess() = runTest {
        // Given
        val sampleUsers = getUserDtos()
        val followedUser = sampleUsers.first().accountId

        coEvery { usersDao.getFollowedUserIds() } returns listOf(followedUser)
        coEvery { usersApi.getTopUsers() } returns UsersResponse(
            items = sampleUsers,
            hasMore = false,
            quotaMax = Int.MAX_VALUE,
            quotaRemaining = 100
        )

        // When
        val result = usersRepository.getTopUsers()

        // Then
        assertEquals(2, result.size)
        assertTrue(result.first().isFollowed) // User 1 is followed
        assertTrue(!result[1].isFollowed) // User 2 is not followed
    }

    @Test
    fun getTopUsersWithoutFollowedSuccess() = runTest {
        // Given
        val sampleUsers = getUserDtos()

        coEvery { usersDao.getFollowedUserIds() } returns emptyList()
        coEvery { usersApi.getTopUsers() } returns UsersResponse(
            items = sampleUsers,
            hasMore = false,
            quotaMax = Int.MAX_VALUE,
            quotaRemaining = 100
        )

        // When
        val result = usersRepository.getTopUsers()

        // Then
        assertEquals(2, result.size)
        assertTrue(!result.first().isFollowed) // User 1 is not followed
        assertTrue(!result[1].isFollowed) // User 2 is not followed
    }

    @Test
    fun getTopUsersFailWithServerError() = runTest {
        // Given
        val sampleUsers = getUserDtos()
        val followedUser = sampleUsers.first().accountId

        coEvery { usersDao.getFollowedUserIds() } returns listOf(followedUser)
        coEvery { usersApi.getTopUsers() } throws createHttpException(500)

        // When
        try {
            usersRepository.getTopUsers()
            fail("Expected AppException to be thrown")
        } catch (appException: AppException) {
            assertEquals(AppError.Server, appException.error)
        }

        // Then
        coVerify { usersDao.getFollowedUserIds() }
    }

    @Test
    fun followUserSuccess() = runTest {
        // Given
        val sampleUser = getUsersList().first()

        // When
        usersRepository.followUser(sampleUser.accountId)

        // Then
        coVerify { usersDao.insertFollowedUser(sampleUser.toEntity()) }
    }

    @Test
    fun followUserFailWithStorageError() = runTest {
        // Given
        val sampleUser = getUsersList().first()

        coEvery { usersDao.insertFollowedUser(sampleUser.toEntity()) } throws SQLiteException()

        // When
        try {
            usersRepository.followUser(sampleUser.accountId)
            fail("Expected AppException to be thrown")
        } catch (appException: AppException) {
            assertEquals(AppError.LocalStorage, appException.error)
        }

        // Then
        coVerify { usersDao.insertFollowedUser(sampleUser.toEntity()) }
    }

    @Test
    fun unFollowUserSuccess() = runTest {
        // Given
        val sampleUser = getUsersList().first()

        // When
        usersRepository.unfollowUser(sampleUser.accountId)

        // Then
        coVerify { usersDao.deleteFollowedUser(sampleUser.accountId) }
    }

    @Test
    fun unFollowUserFailWithStorageError() = runTest {
        // Given
        val sampleUser = getUsersList().first()

        coEvery { usersDao.deleteFollowedUser(sampleUser.accountId) } throws SQLiteException()

        // When
        try {
            usersRepository.unfollowUser(sampleUser.accountId)
            fail("Expected AppException to be thrown")
        } catch (appException: AppException) {
            assertEquals(AppError.LocalStorage, appException.error)
        }

        // Then
        coVerify { usersDao.deleteFollowedUser(sampleUser.accountId) }
    }
}