package com.wiryadev.gitplayground.data.remote

import com.wiryadev.gitplayground.data.remote.model.SearchUserResponse
import com.wiryadev.gitplayground.data.remote.model.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    suspend fun searchUser(
        @Query("q") query: String
    ): SearchUserResponse

    @GET("users/{username}")
    suspend fun getUser(
        @Path("username") username: String
    ): UserResponse

    @GET("users/{username}/followers")
    suspend fun getFollowers(
        @Path("username") username: String,
        @Query("page") page: Int,
        @Query("per_page") size: Int,
    ): List<UserResponse>
}