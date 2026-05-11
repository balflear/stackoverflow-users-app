package com.kgeorgiev.stackoverflowusers.data.mapper

import com.kgeorgiev.stackoverflowusers.UnitTestsHelper.getUser
import com.kgeorgiev.stackoverflowusers.UnitTestsHelper.getUserDto
import junit.framework.TestCase.assertEquals
import org.junit.Test

class UsersMapperTest {

    @Test
    fun testUserDtoToDomain() {
        // Given
        val testDto = getUserDto()

        // When
        val result = testDto.toDomain(true)

        // Then
        assertEquals("John Cena", result.displayName)
        assertEquals(101L, result.accountId)
        assertEquals(10001, result.reputation)
        assertEquals("", result.profileImageUrl)
        assertEquals(true, result.isFollowed)
    }

    @Test
    fun testUserToEntity() {
        // Given
        val testUser = getUser()

        // When
        val result = testUser.toEntity()

        // Then
        assertEquals(101L, result.accountId)
    }
}