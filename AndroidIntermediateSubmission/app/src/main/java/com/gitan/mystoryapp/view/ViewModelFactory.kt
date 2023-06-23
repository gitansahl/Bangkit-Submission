package com.gitan.mystoryapp.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gitan.mystoryapp.data.UserPreference
import com.gitan.mystoryapp.di.Injection
import com.gitan.mystoryapp.view.add_story.AddStoryViewModel
import com.gitan.mystoryapp.view.login.LoginViewModel
import com.gitan.mystoryapp.view.main.MainViewModel
import com.gitan.mystoryapp.view.map.MapsViewModel
import java.lang.IllegalArgumentException


class ViewModelFactory(private val context: Context): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(Injection.provideRepository(context)) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(Injection.provideRepository(context)) as T
            }
            modelClass.isAssignableFrom(AddStoryViewModel::class.java) -> {
                AddStoryViewModel(Injection.provideRepository(context)) as T
            }
            modelClass.isAssignableFrom(MapsViewModel::class.java) -> {
                MapsViewModel(Injection.provideRepository(context)) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}