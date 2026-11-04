package com.cristo.agenda.data.local

interface SessionStorage {
    fun guardarSesion(token: String, nombre: String)
    fun token(): String?
    fun nombre(): String?
    fun borrarSesion()
}