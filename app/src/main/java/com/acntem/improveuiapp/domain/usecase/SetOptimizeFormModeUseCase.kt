package com.acntem.improveuiapp.domain.usecase

import com.acntem.improveuiapp.domain.repository.CommonRepository

class SetOptimizeFormModeUseCase(
    private val repository: CommonRepository,
) {
    suspend operator fun invoke(state: Boolean) {
        repository.setOptimizeFormMode(state)
    }
}