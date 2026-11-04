package com.cristo.agenda.data.mapper

import com.cristo.agenda.data.remote.dto.EventoDto
import com.cristo.agenda.data.remote.dto.EventoRequestDto
import com.cristo.agenda.model.Evento
import com.cristo.agenda.model.TipoEvento

fun EventoDto.toDomain(): Evento = Evento(
    id = id,
    titulo = titulo,
    tipo = if (tipo == "cita") TipoEvento.CITA else TipoEvento.EVENTO,
    fecha = fecha,
    hora = hora,
    descripcion = descripcion,
    duracion = duracion
)

fun Evento.toRequestDto(): EventoRequestDto = EventoRequestDto(
    titulo = titulo,
    tipo = tipo.name.lowercase(),
    fecha = fecha,
    hora = hora,
    descripcion = descripcion,
    duracion = duracion
)