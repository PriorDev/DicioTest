package com.prior.diciotest.feature_users.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface InformationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInformation(informationEntity: InformationEntity)

    @Query("SELECT * FROM InformationEntity WHERE id = :infoId")
    suspend fun getDatosById(infoId: Int): InformationEntity
}