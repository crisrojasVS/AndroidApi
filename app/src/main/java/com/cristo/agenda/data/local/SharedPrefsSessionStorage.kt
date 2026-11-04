package com.cristo.agenda.data.local

import android.content.Context

class SharedPrefsSessionStorage(context: Context) : SessionStorage {

    private val prefs = context.getSharedPreferences("sesion", Context.MODE_PRIVATE)

    override fun guardarSesion(token: String, nombre: String) {
        prefs.edit().putString("token", token).putString("nombre", nombre).apply()
    }

    override fun token(): String? = prefs.getString("token", null)

    override fun nombre(): String? = prefs.getString("nombre", null)

    override fun borrarSesion() {
        prefs.edit().clear().apply()
    }
}