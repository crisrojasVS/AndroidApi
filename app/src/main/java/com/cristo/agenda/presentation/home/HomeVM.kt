package com.cristo.agenda.presentation.home

import androidx.lifecycle.ViewModel
import com.cristo.agenda.domain.repository.AuthRepository

class HomeViewModel(private val authRepository: AuthRepository) : ViewModel() {

    val nombre: String = authRepository.nombreUsuario()

    fun cerrarSesion() {
        authRepository.cerrarSesion()
    }
}