package com.gitan.mystoryapp.data.retrofit

import com.gitan.mystoryapp.data.response.GeneralResponse
import com.gitan.mystoryapp.data.response.GetAllStoriesResponse
import com.gitan.mystoryapp.data.model.LoginData
import com.gitan.mystoryapp.LoginResponse
import com.gitan.mystoryapp.data.model.RegisterData
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {
    @POST("register")
    fun registerUser(
        @Body registerData: RegisterData
    ): Call<GeneralResponse>

    @POST("login")
    fun login(
        @Body loginData: LoginData
    ): Call<LoginResponse>

    @GET("stories")
    suspend fun getAllStories(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): GetAllStoriesResponse

    @GET("stories")
    suspend fun getAllStoriesWithLocation(
        @Header("Authorization") token: String,
        @Query("location") location: Int = 1
    ): GetAllStoriesResponse

    @Multipart
    @POST("stories")
    fun uploadStory(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody
    ): Call<GeneralResponse>
}