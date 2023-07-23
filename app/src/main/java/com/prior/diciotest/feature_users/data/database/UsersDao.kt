package com.prior.diciotest.feature_users.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface UsersDao {
    @Insert(onConflict = REPLACE)
    suspend fun insertUser(userEntity: UserEntity)

    @Transaction
    @Query("Select * From UserEntity")
    suspend fun getUsers(): List<UserWithInfo>
}