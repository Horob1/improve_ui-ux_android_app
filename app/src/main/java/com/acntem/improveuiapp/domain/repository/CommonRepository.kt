package com.acntem.improveuiapp.domain.repository

import com.acntem.improveuiapp.domain.model.BackgroundTheme
import com.acntem.improveuiapp.domain.model.FormInfo
import kotlinx.coroutines.flow.Flow

interface CommonRepository {
    fun getGroupButtonScreenBackground(): Flow<BackgroundTheme>

    suspend fun setGroupButtonScreenBackground(
        backgroundTheme: BackgroundTheme,
    )

    fun getOptimizeGBScreenMode(): Flow<Boolean>

    suspend fun setOptimizeGBScreenMode(
        state: Boolean,
    )

    fun getOptimizeFormMode(): Flow<Boolean>

    suspend fun setOptimizeFormMode(
        state: Boolean,
    )
}