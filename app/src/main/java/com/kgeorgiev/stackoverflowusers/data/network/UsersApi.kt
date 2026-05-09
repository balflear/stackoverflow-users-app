package com.kgeorgiev.stackoverflowusers.data.network

import com.kgeorgiev.stackoverflowusers.data.network.responses.UsersResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface UsersApi {
    @GET("2.2/users")
    suspend fun getTopUsers(
        @Query("page") page: Int = 1,
        @Query("pagesize") pageSize: Int = 20,
        @Query("order") order: String = "desc",
        @Query("sort") sort: String = "reputation",
        @Query("site") site: String = "stackoverflow"
    ): UsersResponse
}