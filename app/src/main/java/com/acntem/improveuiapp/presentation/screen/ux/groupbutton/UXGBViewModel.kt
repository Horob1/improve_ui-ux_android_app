package com.acntem.improveuiapp.presentation.screen.ux.groupbutton

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acntem.improveuiapp.domain.model.BackgroundTheme
import com.acntem.improveuiapp.domain.usecase.GetGBColorUseCase
import com.acntem.improveuiapp.domain.usecase.GetOptimizeGBScreenModeUseCase
import com.acntem.improveuiapp.domain.usecase.SetGBColorUseCase
import com.acntem.improveuiapp.domain.usecase.SetOptimizeGBScreenModeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UXGBViewModel(
    getUXGBUseCase: GetGBColorUseCase,
    private val setGBColorUseCase: SetGBColorUseCase,
    getOptimizeGBScreenModeUseCase: GetOptimizeGBScreenModeUseCase,
    private val setOptimizeGBScreenModeUseCase: SetOptimizeGBScreenModeUseCase,
) : ViewModel() {
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    val state = combine(
        getUXGBUseCase(),
        getOptimizeGBScreenModeUseCase()
    ) { backgroundTheme, mode ->
        GBState(
            backgroundTheme = backgroundTheme,
            mode = mode
        )
    }
        .onStart {
            if (!_isLoading.value) {
                _isLoading.value = true
            }
        }
        .onEach {
            if (_isLoading.value) {
                _isLoading.value = false
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = GBState(
                backgroundTheme = BackgroundTheme.Accent,
                mode = false
            )
        )

    fun setGBColor(color: BackgroundTheme) {
        viewModelScope.launch {
            setGBColorUseCase(color)
        }
    }

    fun setOptimizeGBScreen(mode: Boolean) {
        viewModelScope.launch {
            setOptimizeGBScreenModeUseCase(mode)
        }
    }
}