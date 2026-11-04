package com.cristo.agenda.model

enum class TipoEvento { CITA, EVENTO }

data class Evento(
    val id: Int = 0,
    val titulo: String,
    val tipo: TipoEvento,
    val fecha: String,
    val hora: String? = null,
    val descripcion: String? = null,
    val duracion: Int
)