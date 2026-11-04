package com.cristo.agenda.domain.model

class ApiError(val codigo: Int, mensaje: String) : Exception(mensaje)