package com.prior.diciotest.domain.models

import com.prior.diciotest.feature_users.data.database.InformationEntity
import com.prior.diciotest.feature_users.data.database.UserEntity
import com.prior.diciotest.data.remote.response.UserResponse

data class UserData(
    val apellidoMaterno: String,
    val apellidoPaterno: String,
    val edad: Int?,
    val email: String,
    val fechaNac: String,
    val id: Int,
    val nombre: String,
    val datos: InformationData?
)

fun UserResponse.toDomain() =
    UserData(
        apellidoMaterno = apellidoMaterno,
        apellidoPaterno = apellidoPaterno,
        edad = edad,
        email = email,
        fechaNac = fechaNac,
        id = id,
        nombre = nombre,
        datos = datos?.toDomain()
    )

fun UserEntity.toDomain(datos: InformationEntity?) =
    UserData(
        apellidoMaterno = apellidoMaterno,
        apellidoPaterno = apellidoPaterno,
        edad = edad,
        email = email,
        fechaNac = fechaNac,
        id = id,
        nombre = nombre,
        datos = datos?.toDomain()
    )