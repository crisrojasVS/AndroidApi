package com.cristo.agenda

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.cristo.agenda.presentation.navigation.AppNavigation
import com.cristo.agenda.presentation.navigation.Rutas
import com.cristo.agenda.ui.theme.AgendaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val container = (application as AgendaApp).container
        val inicio = if (container.authRepository.haySesion()) Rutas.HOME else Rutas.LOGIN

        setContent {
            AgendaTheme {
                AppNavigation(inicio = inicio)
            }
        }
    }
}