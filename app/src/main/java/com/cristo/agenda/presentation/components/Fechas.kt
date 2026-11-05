package com.cristo.agenda.presentation.components

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset

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

// "2026-11-05" -> milisegundos, para que el calendario se abra en ese día
fun fechaAMillis(fecha: String): Long? = try {
    LocalDate.parse(fecha).atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
} catch (e: Exception) {
    null
}

// milisegundos que entrega el calendario -> "2026-11-05"
fun millisAFecha(millis: Long): String =
    Instant.ofEpochMilli(millis).atZone(ZoneOffset.UTC).toLocalDate().toString()

// "16:30" -> "4:30 PM"
fun hora12(hora: String): String {
    val (texto, esPm) = partesHora12(hora)
    return if (texto.isEmpty()) hora else "$texto ${if (esPm) "PM" else "AM"}"
}

// "16:30" -> ("4:30", true). El true significa PM.
fun partesHora12(hora: String): Pair<String, Boolean> {
    val partes = hora.split(":")
    val h = partes.getOrNull(0)?.toIntOrNull()
    val m = partes.getOrNull(1)?.toIntOrNull()
    if (h == null || m == null) return Pair("", false)
    val h12 = if (h % 12 == 0) 12 else h % 12
    return Pair("$h12:${m.toString().padStart(2, '0')}", h >= 12)
}

// ("4:30", true) -> "16:30". Si la hora está vacía devuelve null.
fun aHora24(hora: String, esPm: Boolean): String? {
    if (hora.isBlank()) return null
    val partes = hora.trim().split(":")
    val h = partes[0].toInt() % 12 + if (esPm) 12 else 0
    return "${h.toString().padStart(2, '0')}:${partes[1]}"
}