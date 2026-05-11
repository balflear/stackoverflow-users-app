package com.kgeorgiev.stackoverflowusers.domain.usecase

import com.kgeorgiev.stackoverflowusers.UnitTestsHelper.getUsersList
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
class GetTopUsersUseCaseTest {
    private lateinit var getTopUsersUseCase: GetTopUsersUseCase

    private lateinit var usersRepository: UsersRepository

    @Before
    fun setup() {
        usersRepository = mockk(relaxed = true)
        getTopUsersUseCase = GetTopUsersUseCase(usersRepository)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun getTopUsersSuccess() = runTest {
        // Given
        val sampleUsers = getUsersList()
        coEvery { usersRepository.getTopUsers() } returns sampleUsers

        // When
        val result = getTopUsersUseCase()

        // Then
        assertEquals(2, result.size)
        coVerify(exactly = 1) { usersRepository.getTopUsers() }
    }

    @Test
    fun getTopUsersFailWithServerError() = runTest {
        // Given
        coEvery { usersRepository.getTopUsers() } throws AppException(error = AppError.Server)

        // When
        try {
            getTopUsersUseCase()
            fail("Expected AppException to be thrown")
        } catch (appException: AppException) {
            assertEquals(AppError.Server, appException.error)
        }

        // Then
        coVerify(exactly = 1) { usersRepository.getTopUsers() }
    }
}