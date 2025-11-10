package com.acntem.improveuiapp.domain.usecase

import com.acntem.improveuiapp.domain.model.BackgroundTheme
import com.acntem.improveuiapp.domain.repository.CommonRepository

class SetGBColorUseCase(
    private val repository: CommonRepository
) {
    suspend operator fun invoke(
        color: BackgroundTheme
    ) = repository.setGroupButtonScreenBackground(color)
}