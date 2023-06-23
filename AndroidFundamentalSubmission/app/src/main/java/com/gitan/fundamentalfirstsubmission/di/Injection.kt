package com.gitan.fundamentalfirstsubmission.di

import android.content.Context
import com.gitan.fundamentalfirstsubmission.data.FavoriteUserRepository
import com.gitan.fundamentalfirstsubmission.data.local.room.FavoriteUserDatabase
import com.gitan.fundamentalfirstsubmission.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): FavoriteUserRepository {
        val apiService = ApiConfig.getApiService()
        val database = FavoriteUserDatabase.getInstance(context)
        val dao = database.favoriteUserDao()
        return FavoriteUserRepository.getInstance(dao, apiService)
    }
}