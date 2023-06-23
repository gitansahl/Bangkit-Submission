package com.gitan.fundamentalfirstsubmission.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.gitan.fundamentalfirstsubmission.data.local.entity.FavoriteUser
import com.gitan.fundamentalfirstsubmission.data.local.room.FavoriteUserDao
import com.gitan.fundamentalfirstsubmission.data.remote.response.User
import com.gitan.fundamentalfirstsubmission.data.remote.response.UserDataResponse
import com.gitan.fundamentalfirstsubmission.data.remote.retrofit.ApiService

class FavoriteUserRepository private constructor(
    private val favoriteUserDao: FavoriteUserDao,
    private val apiService: ApiService
) {
    fun getFavoriteUsers(): LiveData<List<FavoriteUser>> {
        return favoriteUserDao.getFavoriteUsers()
    }

    fun isUserFavorited(username: String): LiveData<Boolean> = favoriteUserDao.isUserFavorited(username)

    suspend fun setNewAvatarUrl(favoriteUser: FavoriteUser) {
        favoriteUserDao.updateFavoriteUser(favoriteUser)
    }

    suspend fun deleteUser(username: String) {
        favoriteUserDao.deleteFavoriteUser(username)
    }

    suspend fun insertUser(user: FavoriteUser) {
        favoriteUserDao.insertFavoriteUser(user)
    }

    companion object {
        @Volatile
        private var instance: FavoriteUserRepository? = null
        fun getInstance(
            favoriteUserDao: FavoriteUserDao,
            apiService: ApiService
        ): FavoriteUserRepository = instance ?: synchronized(this) {
            instance ?: FavoriteUserRepository(favoriteUserDao, apiService)
        }.also { instance = it }
    }
}