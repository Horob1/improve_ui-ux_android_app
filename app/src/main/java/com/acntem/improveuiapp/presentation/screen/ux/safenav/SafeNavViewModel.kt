package com.acntem.improveuiapp.presentation.screen.ux.safenav

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acntem.improveuiapp.domain.model.SafeNavState
import com.acntem.improveuiapp.domain.usecase.GetSafeNavStateUseCase
import com.acntem.improveuiapp.domain.usecase.SaveSafeNavStateUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SafeNavViewModel(
    getSafeNavStateUseCase: GetSafeNavStateUseCase,
    private val saveSafeNavStateUseCase: SaveSafeNavStateUseCase,
) : ViewModel() {
    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading

    val state = getSafeNavStateUseCase()
        .onStart {
            _isLoading.value = true
        }
        .onEach {
            if (_isLoading.value) _isLoading.value = false
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = SafeNavState(
                optimizeMode = false,
                repeatCount = 1,
                safeBackMode = false
            )
        )

    fun saveSafeNavState(state: SafeNavState) {
        viewModelScope.launch {
            saveSafeNavStateUseCase(state)
        }
    }
}