package com.cristo.agenda.presentation.components

private val mesesCortos =
    listOf("ENE", "FEB", "MAR", "ABR", "MAY", "JUN", "JUL", "AGO", "SEP", "OCT", "NOV", "DIC")
private val mesesLargos = listOf(
    "enero", "febrero", "marzo", "abril", "mayo", "junio",
    "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"
)

fun diaDe(fecha: String): String = fecha.split("-").getOrElse(2) { "" }

fun mesCortoDe(fecha: String): String {
    val mes = fecha.split("-").getOrNull(1)?.toIntOrNull() ?: return ""
    return mesesCortos.getOrElse(mes - 1) { "" }
}

fun fechaLarga(fecha: String): String {
    val partes = fecha.split("-")
    val anio = partes.getOrElse(0) { "" }
    val mes = partes.getOrNull(1)?.toIntOrNull()?.let { mesesLargos.getOrNull(it - 1) } ?: return fecha
    val dia = partes.getOrNull(2)?.toIntOrNull() ?: return fecha
    return "$dia de $mes de $anio"
}