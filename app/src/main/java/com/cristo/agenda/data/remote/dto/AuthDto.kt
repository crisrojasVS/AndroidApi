package com.cristo.agenda.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class RegistroRequestDto(val nombre: String, val correo: String, val password: String)

@Serializable
data class LoginRequestDto(val correo: String, val password: String)

@Serializable
data class UsuarioDto(val id: Int, val nombre: String, val correo: String)

@Serializable
data class AuthResponseDto(val token: String, val usuario: UsuarioDto)