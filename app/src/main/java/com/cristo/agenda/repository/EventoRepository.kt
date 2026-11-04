package com.cristo.agenda.repository

import com.cristo.agenda.model.Evento

interface EventoRepository {
    suspend fun listar(fecha: String? = null, titulo: String? = null): Result<List<Evento>>
    suspend fun obtener(id: Int): Result<Evento>
    suspend fun crear(evento: Evento): Result<Evento>
    suspend fun editar(evento: Evento): Result<Evento>
    suspend fun eliminar(id: Int): Result<Unit>
}