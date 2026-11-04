package com.cristo.agenda.data.remote

import com.cristo.agenda.data.remote.dto.AuthResponseDto
import com.cristo.agenda.data.remote.dto.EventoDto
import com.cristo.agenda.data.remote.dto.EventoRequestDto
import com.cristo.agenda.data.remote.dto.LoginRequestDto
import com.cristo.agenda.data.remote.dto.RegistroRequestDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface AgendaApi {

    @POST("register")
    suspend fun registrar(@Body body: RegistroRequestDto): AuthResponseDto

    @POST("login")
    suspend fun login(@Body body: LoginRequestDto): AuthResponseDto

    @GET("eventos")
    suspend fun listar(
        @Query("fecha") fecha: String?,
        @Query("titulo") titulo: String?
    ): List<EventoDto>

    @GET("eventos/{id}")
    suspend fun obtener(@Path("id") id: Int): EventoDto

    @POST("eventos")
    suspend fun crear(@Body body: EventoRequestDto): EventoDto

    @PUT("eventos/{id}")
    suspend fun editar(@Path("id") id: Int, @Body body: EventoRequestDto): EventoDto

    @DELETE("eventos/{id}")
    suspend fun eliminar(@Path("id") id: Int)
}