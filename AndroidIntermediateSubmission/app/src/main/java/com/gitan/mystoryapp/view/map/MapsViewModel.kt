package com.gitan.mystoryapp.view.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gitan.mystoryapp.data.StoryRepository
import com.gitan.mystoryapp.data.response.ListStoryItem
import kotlinx.coroutines.launch

class MapsViewModel(private val storyRepository: StoryRepository): ViewModel() {

    private val _story = MutableLiveData<List<ListStoryItem>>()
    val story: LiveData<List<ListStoryItem>> = _story

    fun getStory() {
        viewModelScope.launch {
            _story.postValue(storyRepository.getStoriesWithLocation())
        }
    }
}