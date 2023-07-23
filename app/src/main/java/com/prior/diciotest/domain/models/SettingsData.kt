package com.prior.diciotest.domain.models

import com.prior.diciotest.data.database.SettingsEntity

data class SettingsData (
    val id: Int,
    val uri: String
)

fun SettingsEntity.toDomain() =
    SettingsData(
        id, uri
    )