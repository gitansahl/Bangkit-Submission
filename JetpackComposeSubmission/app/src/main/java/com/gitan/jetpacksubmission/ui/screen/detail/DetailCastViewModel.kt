package com.gitan.jetpacksubmission.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gitan.jetpacksubmission.data.CastRepository
import com.gitan.jetpacksubmission.model.Cast
import com.gitan.jetpacksubmission.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailCastViewModel(private val repository: CastRepository): ViewModel() {
    private val _uiState: MutableStateFlow<UiState<Cast>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Cast>> = _uiState

    fun getCastById(castId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getCastById(castId))
        }
    }
}