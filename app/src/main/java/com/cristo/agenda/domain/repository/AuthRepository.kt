package com.cristo.agenda.domain.repository

import com.cristo.agenda.domain.model.Usuario

interface AuthRepository {
    suspend fun registrar(nombre: String, correo: String, password: String): Result<Usuario>
    suspend fun login(correo: String, password: String): Result<Usuario>
    fun cerrarSesion()
    fun haySesion(): Boolean
    fun nombreUsuario(): String
}