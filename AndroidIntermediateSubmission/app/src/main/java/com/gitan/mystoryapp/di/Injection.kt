package com.gitan.mystoryapp.di

import android.content.Context
import com.gitan.mystoryapp.data.StoryRepository
import com.gitan.mystoryapp.data.UserPreference
import com.gitan.mystoryapp.data.retrofit.ApiConfig
import com.gitan.mystoryapp.view.main.dataStore

object Injection {
    fun provideRepository(context: Context): StoryRepository {
        val apiService = ApiConfig.getApiService()
        val pref = UserPreference.getInstance(context.dataStore)
        return StoryRepository(apiService, pref)
    }
}