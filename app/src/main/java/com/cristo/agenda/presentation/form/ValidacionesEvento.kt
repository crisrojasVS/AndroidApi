package com.cristo.agenda.presentation.form

private val regexHora = Regex("^(0?[1-9]|1[0-2]):[0-5]\\d$")

fun validarTitulo(titulo: String): String? = when {
    titulo.isBlank() -> "El título es obligatorio"
    titulo.trim().length > 100 -> "Máximo 100 caracteres"
    else -> null
}

// Con el calendario la fecha siempre llega bien escrita; solo se revisa que se haya elegido.
fun validarFecha(fecha: String): String? =
    if (fecha.isBlank()) "Selecciona una fecha" else null

fun validarHora(hora: String): String? =
    if (hora.isBlank() || regexHora.matches(hora.trim())) null else "Usa el formato h:mm (ejemplo 4:30)"

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