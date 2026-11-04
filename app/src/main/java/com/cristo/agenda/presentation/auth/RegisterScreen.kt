package com.cristo.agenda.presentation.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cristo.agenda.presentation.AppViewModelProvider
import com.cristo.agenda.presentation.components.AppTextField
import com.cristo.agenda.presentation.components.GradientHeader
import com.cristo.agenda.presentation.components.PrimaryButton

@Composable
fun RegisterScreen(
    onRegistroExitoso: () -> Unit,
    onVolver: () -> Unit,
    viewModel: RegisterViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(state.registroExitoso) {
        if (state.registroExitoso) onRegistroExitoso()
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
            Text(
                "Crear cuenta",
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(56.dp))
        }

        Card(
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .offset(y = (-40).dp)
        ) {
            Column(
                modifier = Modifier.padding(22.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                AppTextField(
                    valor = state.nombre,
                    onValorChange = viewModel::onNombreChange,
                    etiqueta = "Nombre",
                    error = state.nombreError
                )
                AppTextField(
                    valor = state.correo,
                    onValorChange = viewModel::onCorreoChange,
                    etiqueta = "Correo",
                    error = state.correoError,
                    tipoTeclado = KeyboardType.Email
                )
                AppTextField(
                    valor = state.password,
                    onValorChange = viewModel::onPasswordChange,
                    etiqueta = "Contraseña",
                    error = state.passwordError,
                    esPassword = true
                )
                AppTextField(
                    valor = state.confirmacion,
                    onValorChange = viewModel::onConfirmacionChange,
                    etiqueta = "Confirmar contraseña",
                    error = state.confirmacionError,
                    esPassword = true
                )
                state.error?.let {
                    Text(it, color = MaterialTheme.colorScheme.error)
                }
                PrimaryButton(
                    texto = "Registrarme",
                    onClick = viewModel::onRegistrarClick,
                    cargando = state.cargando
                )
            }
        }
    }
}