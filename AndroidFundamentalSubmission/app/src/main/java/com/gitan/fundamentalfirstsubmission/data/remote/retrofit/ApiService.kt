package com.gitan.fundamentalfirstsubmission.data.remote.retrofit

import com.gitan.fundamentalfirstsubmission.data.remote.response.GithubResponse
import com.gitan.fundamentalfirstsubmission.data.remote.response.User
import com.gitan.fundamentalfirstsubmission.data.remote.response.UserDataResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun findUsers(
        @Query("q") query: String
    ): Call<GithubResponse>

    @GET("users/{username}")
    fun getUserData(
        @Path("username") username: String
    ): Call<UserDataResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<User>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<User>>
}