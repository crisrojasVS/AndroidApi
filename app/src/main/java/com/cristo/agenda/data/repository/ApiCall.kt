package com.cristo.agenda.data.repository

import com.cristo.agenda.data.remote.dto.ErrorDto
import com.cristo.agenda.model.ApiError
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import java.io.IOException

private val errorJson = Json { ignoreUnknownKeys = true }

suspend fun <T> apiCall(llamada: suspend () -> T): Result<T> {
    return try {
        Result.success(llamada())
    } catch (e: HttpException) {
        Result.failure(ApiError(e.code(), leerMensaje(e)))
    } catch (e: IOException) {
        Result.failure(ApiError(0, "No se pudo conectar con el servidor"))
    } catch (e: SerializationException) {
        Result.failure(ApiError(0, "Respuesta inesperada del servidor"))
    }
}

private fun leerMensaje(e: HttpException): String {
    return try {
        val cuerpo = e.response()?.errorBody()?.string() ?: ""
        errorJson.decodeFromString<ErrorDto>(cuerpo).error
    } catch (ex: Exception) {
        "Error del servidor (${e.code()})"
    }
}