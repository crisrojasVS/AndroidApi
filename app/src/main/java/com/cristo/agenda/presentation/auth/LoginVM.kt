package com.cristo.agenda.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cristo.agenda.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LoginUiState(
    val correo: String = "",
    val password: String = "",
    val correoError: String? = null,
    val passwordError: String? = null,
    val error: String? = null,
    val cargando: Boolean = false,
    val loginExitoso: Boolean = false
)

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onCorreoChange(valor: String) {
        _uiState.update { it.copy(correo = valor, correoError = null, error = null) }
    }

    fun onPasswordChange(valor: String) {
        _uiState.update { it.copy(password = valor, passwordError = null, error = null) }
    }

    fun onLoginClick() {
        val estado = _uiState.value
        val correoError = validarCorreo(estado.correo)
        val passwordError = if (estado.password.isBlank()) "La contraseña es obligatoria" else null

        if (correoError != null || passwordError != null) {
            _uiState.update { it.copy(correoError = correoError, passwordError = passwordError) }
            return
        }

        _uiState.update { it.copy(cargando = true, error = null) }
        viewModelScope.launch {
            authRepository.login(estado.correo.trim(), estado.password)
                .onSuccess {
                    _uiState.update { actual -> actual.copy(cargando = false, loginExitoso = true) }
                }
                .onFailure { e ->
                    _uiState.update { actual ->
                        actual.copy(cargando = false, error = e.message ?: "Ocurrió un error")
                    }
                }
        }
    }
}