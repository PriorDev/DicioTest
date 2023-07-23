package com.prior.diciotest.domain

import com.prior.diciotest.core.Resource
import com.prior.diciotest.data.database.SettingsEntity
import com.prior.diciotest.domain.models.SettingsData
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    suspend fun getSettings(): Flow<SettingsData?>

    suspend fun insert(settings: SettingsEntity)

    suspend fun delete()
}