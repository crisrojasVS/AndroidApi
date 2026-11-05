package com.cristo.agenda.data.mapper

import com.cristo.agenda.data.remote.dto.UsuarioDto
import com.cristo.agenda.model.Usuario

fun UsuarioDto.toDomain(): Usuario = Usuario(id = id, nombre = nombre, correo = correo)