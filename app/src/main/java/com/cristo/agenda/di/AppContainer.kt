package com.cristo.agenda.di

import android.content.Context
import com.cristo.agenda.data.local.SessionStorage
import com.cristo.agenda.data.local.SharedPrefsSessionStorage
import com.cristo.agenda.data.remote.AgendaApi
import com.cristo.agenda.data.remote.AuthInterceptor
import com.cristo.agenda.data.repository.AuthRepositoryImpl
import com.cristo.agenda.data.repository.EventoRepositoryImpl
import com.cristo.agenda.repository.AuthRepository
import com.cristo.agenda.repository.EventoRepository
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

class AppContainer(context: Context) {

    private val sessionStorage: SessionStorage = SharedPrefsSessionStorage(context)

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor(sessionStorage))
        .build()

    private val json = Json { ignoreUnknownKeys = true }

    private val api: AgendaApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
        .create(AgendaApi::class.java)

    val authRepository: AuthRepository = AuthRepositoryImpl(api, sessionStorage)
    val eventoRepository: EventoRepository = EventoRepositoryImpl(api)

    companion object {
        const val BASE_URL = "https://54-209-135-177.sslip.io/"
    }
}