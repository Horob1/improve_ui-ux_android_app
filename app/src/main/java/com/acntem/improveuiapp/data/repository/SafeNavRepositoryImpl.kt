package com.acntem.improveuiapp.data.repository

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.acntem.improveuiapp.domain.model.SafeNavState
import com.acntem.improveuiapp.domain.repository.SafeNavRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.safeNavDataStore by preferencesDataStore(SafeNavRepositoryImpl.SAFE_NAV_DATASTORE)


class SafeNavRepositoryImpl(
    private val context: Context
): SafeNavRepository {

    private val safeNavDataStore = context.safeNavDataStore

    companion object {
        const val SAFE_NAV_DATASTORE = "safe_nav_datastore"
    }

    private object PreferencesKeys {
        val SAFE_NAV_BOOLEAN = booleanPreferencesKey("optimize_mode")
        val SAFE_NAV_REPEAT_COUNT = intPreferencesKey("repeat_count")
        val SAFE_NAV_BACK_MODE = booleanPreferencesKey("back_mode")
    }


    override fun getOptimizeState(): Flow<SafeNavState> {
        return safeNavDataStore.data.map {
            SafeNavState(
                it[PreferencesKeys.SAFE_NAV_BOOLEAN] ?: false,
                it[PreferencesKeys.SAFE_NAV_REPEAT_COUNT] ?: 0,
                it[PreferencesKeys.SAFE_NAV_BACK_MODE] ?: false
            )
        }
    }

    override suspend fun setOptimizeState(state: SafeNavState) {
        safeNavDataStore.edit {
            it[PreferencesKeys.SAFE_NAV_BOOLEAN] = state.optimizeMode
            it[PreferencesKeys.SAFE_NAV_REPEAT_COUNT] = state.repeatCount
            it[PreferencesKeys.SAFE_NAV_BACK_MODE] = state.safeBackMode
        }

    }
}