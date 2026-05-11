package com.kgeorgiev.stackoverflowusers.presentation.users

import com.kgeorgiev.stackoverflowusers.UnitTestsHelper.getUsersList
import com.kgeorgiev.stackoverflowusers.domain.error.AppError
import com.kgeorgiev.stackoverflowusers.domain.error.AppException
import com.kgeorgiev.stackoverflowusers.domain.usecase.FollowUserUseCase
import com.kgeorgiev.stackoverflowusers.domain.usecase.GetTopUsersUseCase
import com.kgeorgiev.stackoverflowusers.domain.usecase.UnFollowUserUseCase
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UsersScreenViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var getTopUsersUseCase: GetTopUsersUseCase
    private lateinit var followUserUseCase: FollowUserUseCase
    private lateinit var unFollowUserUseCase: UnFollowUserUseCase

    private lateinit var viewModel: UsersScreenViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getTopUsersUseCase = mockk(relaxed = true)
        followUserUseCase = mockk(relaxed = true)
        unFollowUserUseCase = mockk(relaxed = true)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun testFetchingTopUsersSuccess() = runTest {
        // Given
        val sampleUsers = getUsersList()
        coEvery { getTopUsersUseCase() } returns sampleUsers

        // When
        viewModel = UsersScreenViewModel(getTopUsersUseCase, followUserUseCase, unFollowUserUseCase)

        // Background collection to keep state updated
        val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.state.collect() }
        advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        assertEquals("List should have 2 items", 2, state.usersList.size)

        collectJob.cancel()
    }

    @Test
    fun testFetchingTopUsersFailWithServerError() = runTest {
        // Given
        coEvery { getTopUsersUseCase() } throws AppException(
            error = AppError.Server,
            cause = Exception("Server error")
        )

        // When
        viewModel = UsersScreenViewModel(getTopUsersUseCase, followUserUseCase, unFollowUserUseCase)

        // Background collection to keep state updated
        val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.state.collect() }
        advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        assertTrue(state.usersList.isEmpty())
        assertEquals(AppError.Server, state.error)

        collectJob.cancel()
    }

    @Test
    fun testFollowingUserSuccess() = runTest {
        // Given
        val sampleUsers = getUsersList()
        val user = getUsersList().first()
        viewModel = UsersScreenViewModel(getTopUsersUseCase, followUserUseCase, unFollowUserUseCase)

        coEvery { getTopUsersUseCase() } returns sampleUsers
        val collectJob = launch(StandardTestDispatcher()) { viewModel.state.collect() }
        // Let init { getTopUsers() } finish first
        advanceUntilIdle()

        // When
        viewModel.handleActions(UsersActions.FollowUser(user.accountId))

        // Wait for handle actions
        advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        assertTrue(
            "User should be followed",
            state.usersList.first { it.accountId == user.accountId }.isFollowed
        )

        collectJob.cancel()
    }

    @Test
    fun testFollowingUserFailedWithStorageError() = runTest {
        // Given
        val sampleUsers = getUsersList()
        val user = sampleUsers.first()

        coEvery { getTopUsersUseCase() } returns sampleUsers
        coEvery { followUserUseCase(user.accountId) } throws AppException(error = AppError.LocalStorage)

        viewModel = UsersScreenViewModel(
            getTopUsersUseCase,
            followUserUseCase,
            unFollowUserUseCase

        )
        val collectJob = launch(StandardTestDispatcher()) { viewModel.state.collect() }
        // Let init { getTopUsers() } finish first
        advanceUntilIdle()

        // When
        viewModel.handleActions(UsersActions.FollowUser(user.accountId))
        // Wait for handle actions
        advanceUntilIdle()

        // Then
        val state = viewModel.state.value

        assertEquals(AppError.LocalStorage, viewModel.state.value.error)
        assertTrue(
            "User follow status shouldn't be changed",
            !state.usersList.first { it.accountId == user.accountId }.isFollowed
        )

        collectJob.cancel()
    }

    @Test
    fun testUnFollowingUserSuccess() = runTest {
        // Given
        val sampleUsers = getUsersList()
        val user = getUsersList().first()
        viewModel = UsersScreenViewModel(getTopUsersUseCase, followUserUseCase, unFollowUserUseCase)

        coEvery { getTopUsersUseCase() } returns sampleUsers
        val collectJob = launch(StandardTestDispatcher()) { viewModel.state.collect() }
        // Let init { getTopUsers() } finish first
        advanceUntilIdle()

        // When
        viewModel.handleActions(UsersActions.UnFollowUser(user.accountId))

        // Wait for handle actions
        advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        assertTrue(
            "User should be un-followed",
            !state.usersList.first { it.accountId == user.accountId }.isFollowed
        )

        collectJob.cancel()
    }

    @Test
    fun testUnFollowingUserFailedWithStorageError() = runTest {
        // Given
        val sampleUsers = getUsersList()
        val user = sampleUsers.first()

        coEvery { getTopUsersUseCase() } returns sampleUsers
        coEvery { unFollowUserUseCase(user.accountId) } throws AppException(error = AppError.LocalStorage)

        viewModel = UsersScreenViewModel(
            getTopUsersUseCase,
            followUserUseCase,
            unFollowUserUseCase

        )
        val collectJob = launch(StandardTestDispatcher()) { viewModel.state.collect() }
        // Let init { getTopUsers() } finish first
        advanceUntilIdle()

        // When
        viewModel.handleActions(UsersActions.UnFollowUser(user.accountId))
        // Wait for handle actions
        advanceUntilIdle()

        // Then
        val state = viewModel.state.value

        assertEquals(AppError.LocalStorage, viewModel.state.value.error)
        assertFalse(
            "User follow status shouldn't be changed",
            state.usersList.first { it.accountId == user.accountId }.isFollowed
        )

        collectJob.cancel()
    }
}

