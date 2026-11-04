package com.cristo.agenda.data.repository

import com.cristo.agenda.data.local.SessionStorage
import com.cristo.agenda.data.mapper.toDomain
import com.cristo.agenda.data.remote.AgendaApi
import com.cristo.agenda.data.remote.dto.AuthResponseDto
import com.cristo.agenda.data.remote.dto.LoginRequestDto
import com.cristo.agenda.data.remote.dto.RegistroRequestDto
import com.cristo.agenda.model.Usuario
import com.cristo.agenda.repository.AuthRepository

class AuthRepositoryImpl(
    private val api: AgendaApi,
    private val session: SessionStorage
) : AuthRepository {

    override suspend fun registrar(nombre: String, correo: String, password: String): Result<Usuario> =
        apiCall { api.registrar(RegistroRequestDto(nombre, correo, password)) }
            .map { guardarSesion(it) }

    override suspend fun login(correo: String, password: String): Result<Usuario> =
        apiCall { api.login(LoginRequestDto(correo, password)) }
            .map { guardarSesion(it) }

    override fun cerrarSesion() = session.borrarSesion()

    override fun haySesion(): Boolean = session.token() != null

    override fun nombreUsuario(): String = session.nombre() ?: ""

    private fun guardarSesion(respuesta: AuthResponseDto): Usuario {
        session.guardarSesion(respuesta.token, respuesta.usuario.nombre)
        return respuesta.usuario.toDomain()
    }
}