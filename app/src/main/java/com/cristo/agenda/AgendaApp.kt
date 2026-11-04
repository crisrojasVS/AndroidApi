package com.cristo.agenda

import android.app.Application
import com.cristo.agenda.di.AppContainer

class AgendaApp : Application() {

    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}