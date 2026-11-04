package com.cristo.agenda.presentation.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.cristo.agenda.presentation.AppViewModelProvider
import com.cristo.agenda.presentation.components.AppTextField
import com.cristo.agenda.presentation.components.GradientHeader
import com.cristo.agenda.presentation.components.PrimaryButton
import com.cristo.agenda.ui.theme.Teal

@Composable
fun LoginScreen(
    onLoginExitoso: () -> Unit,
    onIrARegistro: () -> Unit,
    viewModel: LoginViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(state.loginExitoso) {
        if (state.loginExitoso) onLoginExitoso()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        GradientHeader {
            Spacer(Modifier.height(24.dp))
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(Color.White, RoundedCornerShape(20.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null,
                    tint = Teal,
                    modifier = Modifier.size(34.dp)
                )
            }
            Spacer(Modifier.height(16.dp))
            Text("Agenda", color = Color.White, fontSize = 38.sp, fontWeight = FontWeight.Bold)
            Text(
                "Tus citas y eventos, en un solo lugar",
                color = Color(0xFFE6F4F1),
                fontSize = 16.sp
            )
            Spacer(Modifier.height(64.dp))
        }

        Column(modifier = Modifier.offset(y = (-40).dp)) {
            Card(
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {
                Column(
                    modifier = Modifier.padding(22.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        "Iniciar sesión",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
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
                    state.error?.let {
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                    PrimaryButton(
                        texto = "Entrar",
                        onClick = viewModel::onLoginClick,
                        cargando = state.cargando
                    )
                }
            }
            TextButton(
                onClick = onIrARegistro,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("¿Aún no tienes cuenta? Crear cuenta", fontWeight = FontWeight.Bold)
            }
        }
    }
}