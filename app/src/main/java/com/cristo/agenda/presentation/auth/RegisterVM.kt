package com.cristo.agenda.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cristo.agenda.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class RegisterUiState(
    val nombre: String = "",
    val correo: String = "",
    val password: String = "",
    val confirmacion: String = "",
    val nombreError: String? = null,
    val correoError: String? = null,
    val passwordError: String? = null,
    val confirmacionError: String? = null,
    val error: String? = null,
    val cargando: Boolean = false,
    val registroExitoso: Boolean = false
)

class RegisterViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun onNombreChange(valor: String) {
        _uiState.update { it.copy(nombre = valor, nombreError = null, error = null) }
    }

    fun onCorreoChange(valor: String) {
        _uiState.update { it.copy(correo = valor, correoError = null, error = null) }
    }

    fun onPasswordChange(valor: String) {
        _uiState.update { it.copy(password = valor, passwordError = null, error = null) }
    }

    fun onConfirmacionChange(valor: String) {
        _uiState.update { it.copy(confirmacion = valor, confirmacionError = null, error = null) }
    }

    fun onRegistrarClick() {
        val estado = _uiState.value
        val nombreError = validarNombre(estado.nombre)
        val correoError = validarCorreo(estado.correo)
        val passwordError = validarPassword(estado.password)
        val confirmacionError = validarConfirmacion(estado.password, estado.confirmacion)

        if (nombreError != null || correoError != null ||
            passwordError != null || confirmacionError != null
        ) {
            _uiState.update {
                it.copy(
                    nombreError = nombreError,
                    correoError = correoError,
                    passwordError = passwordError,
                    confirmacionError = confirmacionError
                )
            }
            return
        }

        _uiState.update { it.copy(cargando = true, error = null) }
        viewModelScope.launch {
            authRepository.registrar(estado.nombre.trim(), estado.correo.trim(), estado.password)
                .onSuccess {
                    _uiState.update { actual -> actual.copy(cargando = false, registroExitoso = true) }
                }
                .onFailure { e ->
                    _uiState.update { actual ->
                        actual.copy(cargando = false, error = e.message ?: "Ocurrió un error")
                    }
                }
        }
    }
}