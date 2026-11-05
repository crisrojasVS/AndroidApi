package com.cristo.agenda.presentation.form

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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cristo.agenda.model.TipoEvento
import com.cristo.agenda.presentation.AppViewModelProvider
import com.cristo.agenda.presentation.components.AppTextField
import com.cristo.agenda.presentation.components.GradientHeader
import com.cristo.agenda.presentation.components.PrimaryButton
import com.cristo.agenda.ui.theme.Gray
import com.cristo.agenda.ui.theme.Teal

@Composable
fun FormScreen(
    id: Int,
    onVolver: () -> Unit,
    viewModel: FormViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(id) { viewModel.cargar(id) }
    LaunchedEffect(state.guardado) {
        if (state.guardado) onVolver()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        GradientHeader {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onVolver) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Volver",
                        tint = Color.White
                    )
                }
                Text(
                    text = if (id == 0) "Nuevo evento" else "Editar evento",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(Modifier.height(40.dp))
        }

        if (state.cargando) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(48.dp),
                contentAlignment = Alignment.Center
            ) { CircularProgressIndicator() }
        } else {
            Card(
                shape = RoundedCornerShape(26.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .offset(y = (-30).dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    AppTextField(
                        valor = state.titulo,
                        onValorChange = viewModel::onTituloChange,
                        etiqueta = "Título",
                        error = state.tituloError
                    )

                    Text("Tipo", color = Gray, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        BotonTipo(
                            texto = "Cita",
                            seleccionado = state.tipo == TipoEvento.CITA,
                            onClick = { viewModel.onTipoChange(TipoEvento.CITA) },
                            modifier = Modifier.weight(1f)
                        )
                        BotonTipo(
                            texto = "Evento",
                            seleccionado = state.tipo == TipoEvento.EVENTO,
                            onClick = { viewModel.onTipoChange(TipoEvento.EVENTO) },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    AppTextField(
                        valor = state.fecha,
                        onValorChange = viewModel::onFechaChange,
                        etiqueta = "Fecha (AAAA-MM-DD)",
                        error = state.fechaError
                    )
                    AppTextField(
                        valor = state.hora,
                        onValorChange = viewModel::onHoraChange,
                        etiqueta = "Hora (HH:mm) - opcional",
                        error = state.horaError
                    )
                    AppTextField(
                        valor = state.duracion,
                        onValorChange = viewModel::onDuracionChange,
                        etiqueta = "Duración (minutos)",
                        error = state.duracionError,
                        tipoTeclado = KeyboardType.Number
                    )
                    AppTextField(
                        valor = state.descripcion,
                        onValorChange = viewModel::onDescripcionChange,
                        etiqueta = "Descripción - opcional",
                        error = state.descripcionError
                    )

                    state.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }

                    PrimaryButton(
                        texto = "Guardar",
                        onClick = viewModel::onGuardarClick,
                        cargando = state.guardando
                    )
                }
            }
        }
    }
}

@Composable
private fun BotonTipo(
    texto: String,
    seleccionado: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val forma = RoundedCornerShape(14.dp)
    if (seleccionado) {
        Button(onClick = onClick, shape = forma, modifier = modifier.height(46.dp)) {
            Text(texto, fontWeight = FontWeight.Bold)
        }
    } else {
        OutlinedButton(
            onClick = onClick,
            shape = forma,
            border = BorderStroke(2.dp, Teal),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Teal),
            modifier = modifier.height(46.dp)
        ) {
            Text(texto, fontWeight = FontWeight.Bold)
        }
    }
}