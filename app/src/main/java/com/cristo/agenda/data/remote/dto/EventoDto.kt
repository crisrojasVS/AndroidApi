package com.cristo.agenda.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class EventoDto(
    val id: Int,
    val titulo: String,
    val tipo: String,
    val fecha: String,
    val hora: String? = null,
    val descripcion: String? = null,
    val duracion: Int
)

@Serializable
data class EventoRequestDto(
    val titulo: String,
    val tipo: String,
    val fecha: String,
    val hora: String? = null,
    val descripcion: String? = null,
    val duracion: Int
)