package com.cristo.agenda.model

class ApiError(val codigo: Int, mensaje: String) : Exception(mensaje)