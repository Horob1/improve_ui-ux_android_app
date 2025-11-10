package com.acntem.improveuiapp.presentation.screen.ux.form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acntem.improveuiapp.domain.model.FormInfo
import com.acntem.improveuiapp.domain.usecase.GetOptimizeFormModeUseCase
import com.acntem.improveuiapp.domain.usecase.SetOptimizeFormModeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UXFormViewModel(
    getOptimizeFormModeUseCase: GetOptimizeFormModeUseCase,
    private val setOptimizeFormModeUseCase: SetOptimizeFormModeUseCase,
) : ViewModel() {
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _formErrorState = MutableStateFlow<FormErrorState>(FormErrorState())
    val formErrorState: StateFlow<FormErrorState> = _formErrorState

    private val _formInfo = MutableStateFlow<FormInfo>(FormInfo())
    val formInfo: StateFlow<FormInfo> = _formInfo

    val state = getOptimizeFormModeUseCase()
        .onStart {
        if (!_isLoading.value) {
            _isLoading.value = true
        }
    }.onEach {
        if (_isLoading.value) {
            _isLoading.value = false
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false,
    )

    fun setFormState(
        info: FormInfo,
    ) {
        _formInfo.value = info
    }

    fun setMode(
        mode: Boolean,
    ) {
        viewModelScope.launch {
            setOptimizeFormModeUseCase(mode)
        }
    }

    fun clearFormState() {
        _formInfo.value = FormInfo()
    }

    fun setFormErrorState(
        state: FormErrorState,
    ) {
        _formErrorState.value = state
    }

    fun clearFormErrorState() {
        _formErrorState.value = FormErrorState()
    }
}