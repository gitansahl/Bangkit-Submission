package com.gitan.mystoryapp.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.gitan.mystoryapp.data.StoryRepository
import com.gitan.mystoryapp.data.response.ListStoryItem
import com.gitan.mystoryapp.data.model.User
import kotlinx.coroutines.launch

class MainViewModel(private val storyRepository: StoryRepository): ViewModel() {

    val stories: LiveData<PagingData<ListStoryItem>> = storyRepository.getStories().cachedIn(viewModelScope)

    fun getUser(): LiveData<User> = storyRepository.getUser()

    fun logout() {
        viewModelScope.launch {
            storyRepository.logout()
        }
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}