package com.prior.diciotest.data

import com.prior.diciotest.core.Resource
import com.prior.diciotest.data.database.SettingsDao
import com.prior.diciotest.data.database.SettingsEntity
import com.prior.diciotest.domain.SettingsRepository
import com.prior.diciotest.domain.models.SettingsData
import com.prior.diciotest.domain.models.toDomain
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SettingsRepositoryImp @Inject constructor(
    private val dao: SettingsDao
): SettingsRepository {

    private val refreshIntervalMs: Long = 5000

    override suspend fun getSettings(): Flow<SettingsData?> {
        return flow{
            while (true){
                val settings = dao.getSettings()

                if(settings.isNotEmpty()){
                    emit(settings.first().toDomain())
                }else{
                    emit(null)
                }

                delay(refreshIntervalMs)
            }
        }
    }

    override suspend fun insert(settings: SettingsEntity) {
        dao.insert(settings)
    }

    override suspend fun delete() {
        dao.delete()
    }
}