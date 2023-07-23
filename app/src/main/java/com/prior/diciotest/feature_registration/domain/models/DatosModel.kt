package com.prior.diciotest.feature_registration.domain.models

data class DatosModel(
    val calle:String = "",
    val numero: String = "",
    val colonia: String = "",
    val delegacion: String = "",
    val estado: String = "",
    val cp: String = "",
    val imagen: String = "",
)

fun DatosModel.toApi(): String{
    return "{ \"calle\": \"$calle\",\"numero\": \"$numero\"," +
            "\"colonia\": \"$colonia\",\"delegacion\": \"$delegacion\"," +
            "\"estado\": \"$estado\", \"cp\": \"$cp\", \"imagen\": \"${imagen.replace("[\n\r]", "")}\" }"
}
