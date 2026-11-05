package com.cristo.agenda.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cristo.agenda.model.Evento
import com.cristo.agenda.repository.EventoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class DetailUiState(
    val evento: Evento? = null,
    val cargando: Boolean = true,
    val error: String? = null,
    val mostrarDialogo: Boolean = false,
    val eliminando: Boolean = false,
    val eliminado: Boolean = false
)

class DetailViewModel(private val eventoRepository: EventoRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    fun cargar(id: Int) {
        _uiState.update { it.copy(cargando = it.evento == null, error = null) }
        viewModelScope.launch {
            eventoRepository.obtener(id)
                .onSuccess { evento ->
                    _uiState.update { actual -> actual.copy(evento = evento, cargando = false) }
                }
                .onFailure { e ->
                    _uiState.update { actual ->
                        actual.copy(cargando = false, error = e.message ?: "Ocurrió un error")
                    }
                }
        }
    }

    fun onEliminarClick() {
        _uiState.update { it.copy(mostrarDialogo = true) }
    }

    fun onCancelarEliminar() {
        _uiState.update { it.copy(mostrarDialogo = false) }
    }

    fun onConfirmarEliminar() {
        val id = _uiState.value.evento?.id ?: return
        _uiState.update { it.copy(mostrarDialogo = false, eliminando = true, error = null) }
        viewModelScope.launch {
            eventoRepository.eliminar(id)
                .onSuccess {
                    _uiState.update { actual -> actual.copy(eliminando = false, eliminado = true) }
                }
                .onFailure { e ->
                    _uiState.update { actual ->
                        actual.copy(eliminando = false, error = e.message ?: "Ocurrió un error")
                    }
                }
        }
    }
}