package com.prior.diciotest.feature_registration.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class RegistrationModel (
    val nombre: String = "",
    val apellidoPaterno: String = "",
    val apellidoMaterno: String = "",
    val edad: Int = 0,
    val email: String = "",
    val fechaNac: String = "",
    val datos: String? = null
)