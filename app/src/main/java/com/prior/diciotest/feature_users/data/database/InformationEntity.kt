package com.prior.diciotest.feature_users.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.prior.diciotest.data.remote.response.Datos

@Entity
data class InformationEntity(
    @PrimaryKey(autoGenerate = true) val idInformation: Int? = null,
    val id: Int,
    val calle: String,
    val colonia: String,
    val cp: String?,
    val delegacion: String,
    val estado: String,
    val numero: String
)

fun Datos.toDB(id: Int, imagePath: String): InformationEntity{
    return InformationEntity(
        id = id,
        calle = calle ?: "",
        colonia = colonia?: "",
        cp = cp ?: "",
        delegacion = delegacion?: "",
        estado = estado?: "",
        numero = ""
    )
}
