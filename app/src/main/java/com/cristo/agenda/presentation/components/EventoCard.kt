package com.cristo.agenda.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cristo.agenda.model.Evento
import com.cristo.agenda.model.TipoEvento
import com.cristo.agenda.ui.theme.CitaBackground
import com.cristo.agenda.ui.theme.CitaText
import com.cristo.agenda.ui.theme.EventoBackground
import com.cristo.agenda.ui.theme.EventoText
import com.cristo.agenda.ui.theme.Gray

@Composable
fun TipoChip(tipo: TipoEvento, modifier: Modifier = Modifier) {
    val esCita = tipo == TipoEvento.CITA
    Text(
        text = if (esCita) "Cita" else "Evento",
        color = if (esCita) CitaText else EventoText,
        fontWeight = FontWeight.Bold,
        fontSize = 13.sp,
        modifier = modifier
            .background(if (esCita) CitaBackground else EventoBackground, RoundedCornerShape(50))
            .padding(horizontal = 12.dp, vertical = 4.dp)
    )
}

@Composable
fun EventoCard(evento: Evento, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val esCita = evento.tipo == TipoEvento.CITA
    val fondo = if (esCita) CitaBackground else EventoBackground
    val texto = if (esCita) CitaText else EventoText

    Card(
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .clickable(onClick = onClick)
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Column(
                modifier = Modifier
                    .size(width = 58.dp, height = 66.dp)
                    .background(fondo, RoundedCornerShape(16.dp)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(diaDe(evento.fecha), color = texto, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Text(mesCortoDe(evento.fecha), color = texto, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
            Column {
                Text(evento.titulo, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(
                    text = "${evento.hora ?: "Todo el día"} · ${evento.duracion} min",
                    color = Gray,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 3.dp, bottom = 8.dp)
                )
                TipoChip(evento.tipo)
            }
        }
    }
}