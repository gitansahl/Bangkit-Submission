package com.gitan.mystoryapp.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.gitan.mystoryapp.LoginResult
import com.gitan.mystoryapp.data.StoryRepository
import com.gitan.mystoryapp.data.model.User
import com.gitan.mystoryapp.data.UserPreference
import kotlinx.coroutines.launch

class LoginViewModel(private val storyRepository: StoryRepository): ViewModel() {
    companion object {
        private const val TAG = "LoginViewModel"
    }

    fun getUser(): LiveData<User> = storyRepository.getUser()

    fun login(loginResult: LoginResult) {
        viewModelScope.launch {
            storyRepository.login(loginResult)
        }
    }
}