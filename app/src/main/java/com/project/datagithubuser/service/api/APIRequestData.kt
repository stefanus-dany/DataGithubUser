package com.project.datagithubuser.service.api

import com.project.datagithubuser.service.response.DetailUserResponse
import com.project.datagithubuser.service.response.SearchUsersResponse
import com.project.datagithubuser.service.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface APIRequestData {
    @GET("users")
    fun getAllUsers(
        @Header("Authorization") authToken: String
    ): Call<List<UserResponse>>

    @GET("search/users")
    fun getSearchUsers(
        @Header("Authorization") authToken: String,
        @Query("q") username: String
    ): Call<SearchUsersResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Header("Authorization") authToken: String,
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/following")
    fun getFollowing(
        @Header("Authorization") authToken: String,
        @Path("username") username: String
    ): Call<List<UserResponse>>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Header("Authorization") authToken: String,
        @Path("username") username: String
    ): Call<List<UserResponse>>
}