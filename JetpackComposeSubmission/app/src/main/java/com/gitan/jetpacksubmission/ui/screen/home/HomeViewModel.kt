package com.gitan.jetpacksubmission.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gitan.jetpacksubmission.data.CastRepository
import com.gitan.jetpacksubmission.model.Cast
import com.gitan.jetpacksubmission.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: CastRepository): ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Cast>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Cast>>> = _uiState

    fun getAllCasts() {
        viewModelScope.launch {
            repository.getAllCsts()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { casts ->
                    _uiState.value = UiState.Success(casts)
                }
        }
    }
}