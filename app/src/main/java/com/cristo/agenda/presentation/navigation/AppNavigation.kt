package com.cristo.agenda.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.cristo.agenda.presentation.auth.LoginScreen
import com.cristo.agenda.presentation.auth.RegisterScreen
import com.cristo.agenda.presentation.detail.DetailViewModel
import com.cristo.agenda.presentation.detail.DetailScreen
import com.cristo.agenda.presentation.form.FormScreen
import com.cristo.agenda.presentation.home.HomeScreen

@Composable
fun AppNavigation(inicio: String) {
    val navController = rememberNavController()
    val argumentoId = listOf(navArgument("id") { type = NavType.IntType })

    NavHost(navController = navController, startDestination = inicio) {

        composable(Rutas.LOGIN) {
            LoginScreen(
                onLoginExitoso = {
                    navController.navigate(Rutas.HOME) {
                        popUpTo(Rutas.LOGIN) { inclusive = true }
                    }
                },
                onIrARegistro = { navController.navigate(Rutas.REGISTER) }
            )
        }

        composable(Rutas.REGISTER) {
            RegisterScreen(
                onRegistroExitoso = {
                    navController.navigate(Rutas.HOME) {
                        popUpTo(Rutas.LOGIN) { inclusive = true }
                    }
                },
                onVolver = { navController.popBackStack() }
            )
        }

        composable(Rutas.HOME) {
            HomeScreen(
                onEventoClick = { id -> navController.navigate(Rutas.detalle(id)) },
                onNuevoEvento = { navController.navigate(Rutas.formulario(0)) },
                onCerrarSesion = {
                    navController.navigate(Rutas.LOGIN) {
                        popUpTo(Rutas.HOME) { inclusive = true }
                    }
                }
            )
        }

        composable(Rutas.DETALLE, arguments = argumentoId) { entrada ->
            val id = entrada.arguments?.getInt("id") ?: 0
                DetailScreen(
                id = id,
                onVolver = { navController.popBackStack() },
                onEditar = { navController.navigate(Rutas.formulario(id)) }
            )
        }

        composable(Rutas.FORMULARIO, arguments = argumentoId) { entrada ->
            val id = entrada.arguments?.getInt("id") ?: 0
            FormScreen(
                id = id,
                onVolver = { navController.popBackStack() }
            )
        }
    }
}