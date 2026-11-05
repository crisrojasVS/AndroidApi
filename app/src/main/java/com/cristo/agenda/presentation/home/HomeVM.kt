package com.cristo.agenda.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cristo.agenda.model.ApiError
import com.cristo.agenda.model.Evento
import com.cristo.agenda.repository.AuthRepository
import com.cristo.agenda.repository.EventoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HomeUiState(
    val eventos: List<Evento> = emptyList(),
    val busqueda: String = "",
    val cargando: Boolean = false,
    val error: String? = null,
    val sesionExpirada: Boolean = false
)

class HomeViewModel(
    private val authRepository: AuthRepository,
    private val eventoRepository: EventoRepository
) : ViewModel() {

    val nombre: String = authRepository.nombreUsuario()

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun cargarEventos() {
        _uiState.update { it.copy(cargando = true, error = null) }
        val titulo = _uiState.value.busqueda.trim().ifEmpty { null }

        viewModelScope.launch {
            eventoRepository.listar(titulo = titulo)
                .onSuccess { lista ->
                    _uiState.update { actual -> actual.copy(cargando = false, eventos = lista) }
                }
                .onFailure { e ->
                    if ((e as? ApiError)?.codigo == 401) {
                        authRepository.cerrarSesion()
                        _uiState.update { actual -> actual.copy(cargando = false, sesionExpirada = true) }
                    } else {
                        _uiState.update { actual ->
                            actual.copy(cargando = false, error = e.message ?: "Ocurrió un error")
                        }
                    }
                }
        }
    }

    fun onBusquedaChange(valor: String) {
        _uiState.update { it.copy(busqueda = valor) }
        if (valor.isEmpty()) cargarEventos()
    }

    fun cerrarSesion() {
        authRepository.cerrarSesion()
    }
}