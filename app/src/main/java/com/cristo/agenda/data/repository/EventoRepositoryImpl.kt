package com.cristo.agenda.data.repository

import com.cristo.agenda.data.mapper.toDomain
import com.cristo.agenda.data.mapper.toRequestDto
import com.cristo.agenda.data.remote.AgendaApi
import com.cristo.agenda.domain.model.Evento
import com.cristo.agenda.domain.repository.EventoRepository

class EventoRepositoryImpl(private val api: AgendaApi) : EventoRepository {

    override suspend fun listar(fecha: String?, titulo: String?): Result<List<Evento>> =
        apiCall { api.listar(fecha, titulo).map { it.toDomain() } }

    override suspend fun obtener(id: Int): Result<Evento> =
        apiCall { api.obtener(id).toDomain() }

    override suspend fun crear(evento: Evento): Result<Evento> =
        apiCall { api.crear(evento.toRequestDto()).toDomain() }

    override suspend fun editar(evento: Evento): Result<Evento> =
        apiCall { api.editar(evento.id, evento.toRequestDto()).toDomain() }

    override suspend fun eliminar(id: Int): Result<Unit> =
        apiCall { api.eliminar(id) }
}