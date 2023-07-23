package com.prior.diciotest.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val apellidoMaterno: String,
    val apellidoPaterno: String,
    val edad: Int?,
    val email: String,
    val fechaNac: String,
    val id: Int,
    val nombre: String,
    val datos: Datos? = null
)