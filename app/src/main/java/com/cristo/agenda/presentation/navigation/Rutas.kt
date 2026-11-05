package com.cristo.agenda.presentation.navigation

object Rutas {
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val HOME = "home"
    const val DETALLE = "detalle/{id}"
    const val FORMULARIO = "formulario/{id}"

    fun detalle(id: Int) = "detalle/$id"
    fun formulario(id: Int) = "formulario/$id"
}