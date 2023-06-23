package com.gitan.mystoryapp.view.add_story

import androidx.lifecycle.ViewModel
import com.gitan.mystoryapp.data.StoryRepository
import com.gitan.mystoryapp.data.UserPreference
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class AddStoryViewModel(private val storyRepository: StoryRepository): ViewModel() {
    companion object {
        private const val TAG = "AddStoryViewModel"
    }

    fun getToken(): String = storyRepository.getToken()
}