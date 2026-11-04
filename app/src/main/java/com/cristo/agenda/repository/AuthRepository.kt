package com.cristo.agenda.repository

import com.cristo.agenda.model.Usuario

interface AuthRepository {
    suspend fun registrar(nombre: String, correo: String, password: String): Result<Usuario>
    suspend fun login(correo: String, password: String): Result<Usuario>
    fun cerrarSesion()
    fun haySesion(): Boolean
    fun nombreUsuario(): String
}