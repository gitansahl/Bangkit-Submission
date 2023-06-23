package com.gitan.fundamentalfirstsubmission.ui

import androidx.lifecycle.ViewModel
import com.gitan.fundamentalfirstsubmission.data.FavoriteUserRepository

class FavoriteUserViewModel(private val favoriteUserRepository: FavoriteUserRepository): ViewModel() {
    fun getFavoriteUser() = favoriteUserRepository.getFavoriteUsers()

}