package com.gitan.fundamentalfirstsubmission.ui

import android.util.Log
import androidx.lifecycle.*
import com.gitan.fundamentalfirstsubmission.data.local.SettingPreferences
import com.gitan.fundamentalfirstsubmission.data.remote.response.GithubResponse
import com.gitan.fundamentalfirstsubmission.data.remote.response.User
import com.gitan.fundamentalfirstsubmission.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {

    private val _listUser = MutableLiveData<List<User>>()
    val listUser: LiveData<List<User>> = _listUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private lateinit var pref: SettingPreferences

    companion object {
        private const val TAG = "SearchRequest"
    }

    init {
        findUser("gitan")
    }

    fun findUser(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().findUsers(query = query)
        client.enqueue(object: Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listUser.value = response.body()?.items
                } else {
                    Log.e(TAG,"onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
    fun setPreferences(preferences: SettingPreferences) {
        this.pref = preferences
    }
    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }
    fun saveThemeSetting(isDarkmodeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkmodeActive)
        }
    }
}