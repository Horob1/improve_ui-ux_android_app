package com.acntem.improveuiapp.domain.repository

import com.acntem.improveuiapp.domain.model.SafeNavState
import kotlinx.coroutines.flow.Flow

interface SafeNavRepository {
    fun getOptimizeState() : Flow<SafeNavState>

    suspend fun setOptimizeState(state: SafeNavState)
}