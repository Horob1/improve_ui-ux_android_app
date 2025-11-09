package com.acntem.improveuiapp.data.repository

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.acntem.improveuiapp.domain.model.BackgroundTheme
import com.acntem.improveuiapp.domain.model.FormInfo
import com.acntem.improveuiapp.domain.repository.CommonRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.commonDataStore by preferencesDataStore(CommonRepositoryImpl.COMMON_DATA_STORE)


class CommonRepositoryImpl(context: Context) : CommonRepository {

    private val dataStore = context.commonDataStore

    private val gson = Gson()


    companion object {
        const val COMMON_DATA_STORE = "common_data_store"
    }

    private object PreferencesKeys {
        val GROUP_BUTTON_SCREEN_BACKGROUND = stringPreferencesKey("group_button_screen_background")

        val ENABLE_OPTIMIZE_MODE_IN_GBS = booleanPreferencesKey("enable_optimize_mode_in_gbs")

        val ENABLE_OPTIMIZE_MODE_IN_FORM = booleanPreferencesKey("enable_optimize_mode_in_form")

    }

    override fun getGroupButtonScreenBackground(): Flow<BackgroundTheme> {
        return dataStore.data.map { preferences ->
            when (preferences[PreferencesKeys.GROUP_BUTTON_SCREEN_BACKGROUND]) {
                BackgroundTheme.Accent.name -> BackgroundTheme.Accent
                BackgroundTheme.Dark.name -> BackgroundTheme.Dark
                else -> BackgroundTheme.Medium
            }
        }
    }

    override suspend fun setGroupButtonScreenBackground(backgroundTheme: BackgroundTheme) {
        dataStore.edit {
            it[PreferencesKeys.GROUP_BUTTON_SCREEN_BACKGROUND] = backgroundTheme.name
        }
    }

    override fun getOptimizeGBScreenMode(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[PreferencesKeys.ENABLE_OPTIMIZE_MODE_IN_GBS] ?: false
        }
    }

    override suspend fun setOptimizeGBScreenMode(state: Boolean) {
        dataStore.edit {
            it[PreferencesKeys.ENABLE_OPTIMIZE_MODE_IN_GBS] = state
        }
    }

    override fun getOptimizeFormMode(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[PreferencesKeys.ENABLE_OPTIMIZE_MODE_IN_FORM] ?: false
        }
    }

    override suspend fun setOptimizeFormMode(state: Boolean) {
        dataStore.edit {
            it[PreferencesKeys.ENABLE_OPTIMIZE_MODE_IN_FORM] = state
        }
    }
}