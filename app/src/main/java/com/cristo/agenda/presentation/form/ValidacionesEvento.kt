package com.cristo.agenda.presentation.form

private val regexHora = Regex("^([01]\\d|2[0-3]):[0-5]\\d$")

fun validarTitulo(titulo: String): String? = when {
    titulo.isBlank() -> "El título es obligatorio"
    titulo.trim().length > 100 -> "Máximo 100 caracteres"
    else -> null
}

fun validarFecha(fecha: String): String? {
    val partes = fecha.trim().split("-")
    if (partes.size != 3 || partes[0].length != 4 || partes[1].length != 2 || partes[2].length != 2) {
        return "Usa el formato AAAA-MM-DD"
    }
    val anio = partes[0].toIntOrNull()
    val mes = partes[1].toIntOrNull()
    val dia = partes[2].toIntOrNull()
    if (anio == null || mes == null || dia == null || mes !in 1..12) return "La fecha no es válida"

    val diasDelMes = when (mes) {
        2 -> if (anio % 4 == 0 && (anio % 100 != 0 || anio % 400 == 0)) 29 else 28
        4, 6, 9, 11 -> 30
        else -> 31
    }
    return if (dia in 1..diasDelMes) null else "La fecha no es válida"
}

fun validarHora(hora: String): String? =
    if (hora.isBlank() || regexHora.matches(hora.trim())) null else "Usa el formato HH:mm (ejemplo 16:30)"

fun validarDescripcion(descripcion: String): String? =
    if (descripcion.trim().length > 255) "Máximo 255 caracteres" else null

fun validarDuracion(duracion: String): String? {
    val numero = duracion.trim().toIntOrNull()
    return when {
        duracion.isBlank() -> "La duración es obligatoria"
        numero == null -> "Debe ser un número"
        numero !in 1..1440 -> "Debe estar entre 1 y 1440 minutos"
        else -> null
    }
}