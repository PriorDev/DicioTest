package com.prior.diciotest.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class Datos(
    val calle: String? = null,
    val codigoPostal: Int? = null,
    val colonia: String? = null,
    val cp: String? = null,
    val delegacion: String? = null,
    val estado: String? = null,
    val imagen: String? = null,
)