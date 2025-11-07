package com.acntem.improveuiapp.domain.usecase

import com.acntem.improveuiapp.domain.repository.SafeNavRepository

class GetSafeNavStateUseCase(
    private val safeNavRepository: SafeNavRepository
) {
    operator fun invoke() = safeNavRepository.getOptimizeState()
}