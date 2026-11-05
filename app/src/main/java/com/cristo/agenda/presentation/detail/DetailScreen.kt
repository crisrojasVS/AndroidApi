package com.cristo.agenda.presentation.detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cristo.agenda.presentation.AppViewModelProvider
import com.cristo.agenda.presentation.components.GradientHeader
import com.cristo.agenda.presentation.components.PrimaryButton
import com.cristo.agenda.presentation.components.TipoChip
import com.cristo.agenda.presentation.components.fechaLarga
import com.cristo.agenda.ui.theme.Coral
import com.cristo.agenda.ui.theme.Gray

@Composable
fun DetailScreen(
    id: Int,
    onVolver: () -> Unit,
    onEditar: () -> Unit,
    viewModel: DetailViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val evento = state.evento

    LaunchedEffect(id) { viewModel.cargar(id) }
    LaunchedEffect(state.eliminado) {
        if (state.eliminado) onVolver()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        GradientHeader {
            IconButton(onClick = onVolver) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver",
                    tint = Color.White
                )
            }
            if (evento != null) {
                Spacer(Modifier.height(8.dp))
                TipoChip(evento.tipo)
                Spacer(Modifier.height(10.dp))
                Text(evento.titulo, color = Color.White, fontSize = 34.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.height(48.dp))
        }

        when {
            state.cargando -> Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(48.dp),
                contentAlignment = Alignment.Center
            ) { CircularProgressIndicator() }

            evento == null -> Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                Text(
                    state.error ?: "No se pudo cargar el evento",
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(12.dp))
                Button(onClick = { viewModel.cargar(id) }) { Text("Reintentar") }
            }

            else -> {
                Card(
                    shape = RoundedCornerShape(26.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .offset(y = (-36).dp)
                ) {
                    Column(
                        modifier = Modifier.padding(22.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Campo("FECHA", fechaLarga(evento.fecha))
                        Campo("HORA", evento.hora ?: "Todo el día")
                        Campo("DURACIÓN", "${evento.duracion} minutos")
                        Campo("DESCRIPCIÓN", evento.descripcion ?: "Sin descripción")
                    }
                }

                Column(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .offset(y = (-20).dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    state.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                    PrimaryButton(texto = "Editar", onClick = onEditar)
                    OutlinedButton(
                        onClick = viewModel::onEliminarClick,
                        enabled = !state.eliminando,
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(2.dp, Coral),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Coral),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                    ) {
                        Text(
                            if (state.eliminando) "Eliminando..." else "Eliminar",
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp
                        )
                    }
                }
            }
        }
    }

    if (state.mostrarDialogo && evento != null) {
        AlertDialog(
            onDismissRequest = viewModel::onCancelarEliminar,
            title = { Text("Eliminar evento") },
            text = { Text("¿Seguro que quieres eliminar \"${evento.titulo}\"? Esta acción no se puede deshacer.") },
            confirmButton = {
                TextButton(onClick = viewModel::onConfirmarEliminar) {
                    Text("Eliminar", color = Coral, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = viewModel::onCancelarEliminar) { Text("Cancelar") }
            }
        )
    }
}

@Composable
private fun Campo(titulo: String, valor: String) {
    Column {
        Text(titulo, color = Gray, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        Text(valor, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
    }
}