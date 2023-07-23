package com.prior.diciotest.domain.models

import androidx.compose.ui.graphics.ImageBitmap
import com.prior.diciotest.core.toImageBitMap
import com.prior.diciotest.feature_users.data.database.InformationEntity
import com.prior.diciotest.data.remote.response.Datos

data class InformationData(
    val calle: String,
    val numero: String,
    val colonia: String,
    val codigoPostal: Int? = null,
    val cp: String? = null,
    val delegacion: String,
    val estado: String,
    val imagen: ImageBitmap?
)

fun Datos.toDomain(): InformationData {
    val image = if(!this.imagen.isNullOrBlank()) this.imagen.toImageBitMap() else null

    return InformationData(
        calle = calle?: "",
        codigoPostal = codigoPostal,
        colonia = colonia?: "",
        cp = cp,
        delegacion = delegacion?: "",
        estado = estado?: "",
        imagen = image,
        numero = ""
    )
}

fun InformationEntity.toDomain(): InformationData {
    return InformationData(
        calle = calle,
        codigoPostal = 0,
        colonia = colonia,
        cp = cp,
        delegacion = delegacion,
        estado = estado,
        imagen = null,
        numero = numero
    )
}
