package com.cristo.agenda.presentation

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.cristo.agenda.AgendaApp
import com.cristo.agenda.presentation.auth.LoginViewModel
import com.cristo.agenda.presentation.auth.RegisterViewModel
import com.cristo.agenda.presentation.detail.DetailViewModel
import com.cristo.agenda.presentation.form.FormViewModel
import com.cristo.agenda.presentation.home.HomeViewModel

object AppViewModelProvider {

    val Factory = viewModelFactory {
        initializer { LoginViewModel(agendaApp().container.authRepository) }
        initializer { RegisterViewModel(agendaApp().container.authRepository) }
        initializer {
            HomeViewModel(
                agendaApp().container.authRepository,
                agendaApp().container.eventoRepository
            )
        }
        initializer { DetailViewModel(agendaApp().container.eventoRepository) }
        initializer { FormViewModel(agendaApp().container.eventoRepository) }
    }
}

private fun CreationExtras.agendaApp(): AgendaApp =
    this[APPLICATION_KEY] as AgendaApp