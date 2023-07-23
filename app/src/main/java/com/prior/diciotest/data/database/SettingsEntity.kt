package com.prior.diciotest.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.prior.diciotest.domain.models.SettingsData

@Entity
data class SettingsEntity(
    @PrimaryKey
    val id: Int,
    val uri: String
)

fun SettingsData.toDB() =
    SettingsEntity(
        id, uri
    )
