package com.prior.diciotest.feature_users.data.database

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithInfo(
    @Embedded
    val user: UserEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    )
    val information: InformationEntity?,
)
