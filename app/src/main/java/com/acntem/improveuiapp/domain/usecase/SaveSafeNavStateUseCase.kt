package com.acntem.improveuiapp.domain.usecase

import com.acntem.improveuiapp.domain.model.SafeNavState
import com.acntem.improveuiapp.domain.repository.SafeNavRepository

class SaveSafeNavStateUseCase(
    private val safeNavRepository: SafeNavRepository
) {
    suspend operator fun invoke(
        state: SafeNavState
    ) = safeNavRepository.setOptimizeState(state)
}