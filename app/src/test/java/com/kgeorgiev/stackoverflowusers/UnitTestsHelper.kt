package com.kgeorgiev.stackoverflowusers

import com.kgeorgiev.stackoverflowusers.data.network.responses.UserDto
import com.kgeorgiev.stackoverflowusers.domain.model.User
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response

object UnitTestsHelper {
    private val user1 = User(
        accountId = 101L,
        displayName = "John Cena",
        reputation = 10001,
        profileImageUrl = ""
    )

    private val user2 = User(
        accountId = 105L,
        displayName = "The Rock",
        reputation = 788888,
        profileImageUrl = ""
    )

    private val userDto1 = UserDto(
        accountId = 101L,
        displayName = "John Cena",
        reputation = 10001,
        profileImageUrl = "",
        userId = 102L
    )

    private val userDto2 = UserDto(
        accountId = 103L,
        displayName = "The Rock",
        reputation = 7777777,
        profileImageUrl = "",
        userId = 105L
    )

    fun getUsersList(): List<User> = listOf<User>(user1, user2)


    fun getUserDto() = UserDto(
        accountId = 101L,
        displayName = "John Cena",
        reputation = 10001,
        profileImageUrl = "",
        userId = 102L
    )

    fun getUserDtos() = listOf(userDto1, userDto2)

    fun getUser() = user1

    fun createHttpException(
        code: Int = 500,
        errorBody: String = """{"error":"Server error"}"""
    ): HttpException {
        val responseBody = errorBody.toResponseBody("application/json".toMediaType())
        val errorResponse = Response.error<Any>(code, responseBody)
        return HttpException(errorResponse)
    }
}