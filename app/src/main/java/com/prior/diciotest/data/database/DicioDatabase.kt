package com.prior.diciotest.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.prior.diciotest.feature_users.data.database.InformationDao
import com.prior.diciotest.feature_users.data.database.InformationEntity
import com.prior.diciotest.feature_users.data.database.UserEntity
import com.prior.diciotest.feature_users.data.database.UsersDao

@Database(
    entities = [
        UserEntity::class,
        InformationEntity::class,
        SettingsEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class DicioDatabase: RoomDatabase() {
    abstract val userDao: UsersDao
    abstract val infoDao: InformationDao
    abstract val settingsDao: SettingsDao

    companion object{
        const val NAME = "DICIO_DATABASE"
    }
}