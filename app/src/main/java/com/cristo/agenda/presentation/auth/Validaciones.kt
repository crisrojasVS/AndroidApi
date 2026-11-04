package com.cristo.agenda.presentation.auth

private val regexCorreo = Regex("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$")

fun validarNombre(nombre: String): String? =
    if (nombre.isBlank()) "El nombre es obligatorio" else null

fun validarCorreo(correo: String): String? = when {
    correo.isBlank() -> "El correo es obligatorio"
    !regexCorreo.matches(correo.trim()) -> "El correo no tiene un formato válido"
    else -> null
}

fun validarPassword(password: String): String? = when {
    password.isBlank() -> "La contraseña es obligatoria"
    password.length < 8 -> "Debe tener al menos 8 caracteres"
    else -> null
}

fun validarConfirmacion(password: String, confirmacion: String): String? = when {
    confirmacion.isBlank() -> "Confirma tu contraseña"
    confirmacion != password -> "Las contraseñas no coinciden"
    else -> null
}