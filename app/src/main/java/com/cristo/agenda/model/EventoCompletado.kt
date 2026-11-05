package com.cristo.agenda.model

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

// Un evento está completado cuando ya pasó su hora de fin (hora + duración).
// Si no tiene hora, cuando ya terminó ese día.
fun Evento.estaCompletado(): Boolean {
    return try {
        val dia = LocalDate.parse(fecha)
        val horaInicio = hora
        val fin = if (horaInicio == null) {
            dia.plusDays(1).atStartOfDay()
        } else {
            val partes = horaInicio.split(":")
            LocalDateTime.of(dia, LocalTime.of(partes[0].toInt(), partes[1].toInt()))
                .plusMinutes(duracion.toLong())
        }
        LocalDateTime.now().isAfter(fin)
    } catch (e: Exception) {
        false
    }
}