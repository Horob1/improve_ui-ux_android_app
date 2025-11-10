package com.acntem.improveuiapp.domain.usecase

import com.acntem.improveuiapp.domain.repository.CommonRepository

class GetOptimizeFormModeUseCase(
    private val repository: CommonRepository,
) {
    operator fun invoke() = repository.getOptimizeFormMode()
}