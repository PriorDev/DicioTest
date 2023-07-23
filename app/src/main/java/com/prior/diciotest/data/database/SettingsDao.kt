package com.prior.diciotest.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update

@Dao
interface SettingsDao {
    @Query("select * from settingsentity")
    suspend fun getSettings(): List<SettingsEntity>

    @Insert(onConflict = REPLACE)
    suspend fun insert(settingsEntity: SettingsEntity)

    @Query("delete from settingsentity")
    suspend fun delete()
}