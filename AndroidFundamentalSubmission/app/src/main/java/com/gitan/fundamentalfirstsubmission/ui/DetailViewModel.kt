package com.gitan.fundamentalfirstsubmission.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gitan.fundamentalfirstsubmission.data.FavoriteUserRepository
import com.gitan.fundamentalfirstsubmission.data.local.entity.FavoriteUser
import com.gitan.fundamentalfirstsubmission.data.remote.response.UserDataResponse
import com.gitan.fundamentalfirstsubmission.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(private val favoriteUserRepository: FavoriteUserRepository): ViewModel() {

    private val _userData = MutableLiveData<UserDataResponse>()
    val userData: LiveData<UserDataResponse> = _userData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "DetailRequest"
    }

    fun getUserDetail(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserData(username)
        client.enqueue(object: Callback<UserDataResponse> {
            override fun onResponse(
                call: Call<UserDataResponse>,
                response: Response<UserDataResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _userData.value = response.body()
                    val updatedUser = FavoriteUser(response.body()?.login ?: "", response.body()?.avatarUrl)
                    updateUser(updatedUser)
                } else {
                    Log.e(TAG,"onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserDataResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun isUserFavorited(username: String): LiveData<Boolean> = favoriteUserRepository.isUserFavorited(username)

    fun removeUser(username: String) {
        viewModelScope.launch {
            favoriteUserRepository.deleteUser(username)
        }
    }

    fun insertUser(user: FavoriteUser) {
        viewModelScope.launch {
            favoriteUserRepository.insertUser(user)
        }
    }

    fun updateUser(user: FavoriteUser) {
        viewModelScope.launch {
            favoriteUserRepository.setNewAvatarUrl(user)
        }
    }
}