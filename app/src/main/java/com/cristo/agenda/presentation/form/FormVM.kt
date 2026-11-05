package com.cristo.agenda.presentation.form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cristo.agenda.model.Evento
import com.cristo.agenda.model.TipoEvento
import com.cristo.agenda.repository.EventoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class FormUiState(
    val id: Int = 0,
    val titulo: String = "",
    val tipo: TipoEvento = TipoEvento.CITA,
    val fecha: String = "",
    val hora: String = "",
    val descripcion: String = "",
    val duracion: String = "",
    val tituloError: String? = null,
    val fechaError: String? = null,
    val horaError: String? = null,
    val descripcionError: String? = null,
    val duracionError: String? = null,
    val error: String? = null,
    val cargando: Boolean = false,
    val guardando: Boolean = false,
    val guardado: Boolean = false
)

class FormViewModel(private val eventoRepository: EventoRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(FormUiState())
    val uiState: StateFlow<FormUiState> = _uiState.asStateFlow()

    private var idCargado = -1

    // id = 0 -> evento nuevo. Otro id -> se cargan sus datos para editarlo.
    fun cargar(id: Int) {
        if (id == idCargado) return
        idCargado = id
        if (id == 0) return

        _uiState.update { it.copy(cargando = true) }
        viewModelScope.launch {
            eventoRepository.obtener(id)
                .onSuccess { evento ->
                    _uiState.update { actual ->
                        actual.copy(
                            id = evento.id,
                            titulo = evento.titulo,
                            tipo = evento.tipo,
                            fecha = evento.fecha,
                            hora = evento.hora ?: "",
                            descripcion = evento.descripcion ?: "",
                            duracion = evento.duracion.toString(),
                            cargando = false
                        )
                    }
                }
                .onFailure { e ->
                    _uiState.update { actual ->
                        actual.copy(cargando = false, error = e.message ?: "Ocurrió un error")
                    }
                }
        }
    }

    fun onTituloChange(valor: String) {
        _uiState.update { it.copy(titulo = valor, tituloError = null, error = null) }
    }

    fun onTipoChange(valor: TipoEvento) {
        _uiState.update { it.copy(tipo = valor) }
    }

    fun onFechaChange(valor: String) {
        _uiState.update { it.copy(fecha = valor, fechaError = null, error = null) }
    }

    fun onHoraChange(valor: String) {
        _uiState.update { it.copy(hora = valor, horaError = null, error = null) }
    }

    fun onDuracionChange(valor: String) {
        _uiState.update { it.copy(duracion = valor, duracionError = null, error = null) }
    }

    fun onDescripcionChange(valor: String) {
        _uiState.update { it.copy(descripcion = valor, descripcionError = null, error = null) }
    }

    fun onGuardarClick() {
        val estado = _uiState.value
        val tituloError = validarTitulo(estado.titulo)
        val fechaError = validarFecha(estado.fecha)
        val horaError = validarHora(estado.hora)
        val descripcionError = validarDescripcion(estado.descripcion)
        val duracionError = validarDuracion(estado.duracion)

        val hayErrores = listOf(tituloError, fechaError, horaError, descripcionError, duracionError)
            .any { it != null }
        if (hayErrores) {
            _uiState.update {
                it.copy(
                    tituloError = tituloError,
                    fechaError = fechaError,
                    horaError = horaError,
                    descripcionError = descripcionError,
                    duracionError = duracionError
                )
            }
            return
        }

        val evento = Evento(
            id = estado.id,
            titulo = estado.titulo.trim(),
            tipo = estado.tipo,
            fecha = estado.fecha.trim(),
            hora = estado.hora.trim().ifEmpty { null },
            descripcion = estado.descripcion.trim().ifEmpty { null },
            duracion = estado.duracion.trim().toInt()
        )

        _uiState.update { it.copy(guardando = true, error = null) }
        viewModelScope.launch {
            val resultado = if (estado.id == 0) {
                eventoRepository.crear(evento)
            } else {
                eventoRepository.editar(evento)
            }
            resultado
                .onSuccess {
                    _uiState.update { actual -> actual.copy(guardando = false, guardado = true) }
                }
                .onFailure { e ->
                    _uiState.update { actual ->
                        actual.copy(guardando = false, error = e.message ?: "Ocurrió un error")
                    }
                }
        }
    }
}