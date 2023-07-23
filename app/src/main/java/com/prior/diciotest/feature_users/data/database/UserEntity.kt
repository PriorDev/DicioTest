package com.prior.diciotest.feature_users.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.prior.diciotest.data.remote.response.UserResponse

@Entity
data class UserEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val apellidoMaterno: String,
    val apellidoPaterno: String,
    val edad: Int?,
    val email: String,
    val fechaNac: String,
    val nombre: String
)

fun UserResponse.toDB() =
    UserEntity(
        id = id,
        apellidoMaterno = apellidoMaterno,
        apellidoPaterno = apellidoPaterno,
        edad = edad,
        email = email,
        fechaNac = fechaNac,
        nombre = nombre
    )