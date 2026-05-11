package com.kgeorgiev.stackoverflowusers.domain.usecase

import com.kgeorgiev.stackoverflowusers.domain.error.AppError
import com.kgeorgiev.stackoverflowusers.domain.error.AppException
import com.kgeorgiev.stackoverflowusers.domain.repository.UsersRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.fail
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UnFollowUserUseCaseTest {
    private lateinit var unFollowUserUseCase: UnFollowUserUseCase

    private lateinit var usersRepository: UsersRepository

    @Before
    fun setup() {
        usersRepository = mockk(relaxed = true)
        unFollowUserUseCase = UnFollowUserUseCase(usersRepository)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun unFollowUserSuccess() = runTest {
        // Given
        val accountId = 101L
        coEvery { usersRepository.followUser(accountId) } returns Unit

        // When
        val result = unFollowUserUseCase(accountId)

        // Then
        assertEquals(Unit, result)
        coVerify(exactly = 1) { usersRepository.unfollowUser(accountId) }
    }

    @Test

    fun unFollowUserFailWithStorageError() = runTest {
        // Given
        val accountId = 101L
        coEvery { usersRepository.unfollowUser(accountId) } throws AppException(
            error = AppError.LocalStorage
        )

        // When
        try {
            unFollowUserUseCase(accountId)
            fail("Expected AppException to be thrown")
        } catch (exception: AppException) {
            assertEquals(AppError.LocalStorage, exception.error)
        }

        // Then
        coVerify(exactly = 1) { usersRepository.unfollowUser(accountId) }
    }
}