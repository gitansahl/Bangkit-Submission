package com.gitan.mystoryapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.gitan.mystoryapp.LoginResult
import com.gitan.mystoryapp.data.model.User
import com.gitan.mystoryapp.data.response.ListStoryItem
import com.gitan.mystoryapp.data.retrofit.ApiService
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class StoryRepository(private val apiService: ApiService, private val pref: UserPreference) {
    fun getStories(): LiveData<PagingData<ListStoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StoryPagingSource(apiService, getToken())
            }
        ).liveData
    }

    suspend fun getStoriesWithLocation(): List<ListStoryItem> {
        return apiService.getAllStoriesWithLocation("Bearer ${getToken()}").listStory
    }

    fun getToken(): String {
        val token = runBlocking {
            pref.getToken().first()
        }
        Log.d("StoryRepository", token)
        return token
    }

    fun getUser(): LiveData<User> {
        return pref.getUser().asLiveData()
    }

    suspend fun logout() {
        pref.logout()
    }

    suspend fun login(loginResult: LoginResult) {
        val user = User(
            name = loginResult.name,
            userId = loginResult.userId,
            token = loginResult.token,
            isLogin = true
        )
        pref.saveUser(user)
    }
}
