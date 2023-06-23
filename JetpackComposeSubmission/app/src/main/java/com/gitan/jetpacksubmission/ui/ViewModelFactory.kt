package com.gitan.jetpacksubmission.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gitan.jetpacksubmission.data.CastRepository
import com.gitan.jetpacksubmission.ui.screen.detail.DetailCastViewModel
import com.gitan.jetpacksubmission.ui.screen.home.HomeViewModel

class ViewModelFactory(private val repository: CastRepository): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(DetailCastViewModel::class.java)) {
            return DetailCastViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}