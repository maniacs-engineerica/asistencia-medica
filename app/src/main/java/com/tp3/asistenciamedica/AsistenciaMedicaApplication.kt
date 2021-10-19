package com.tp3.asistenciamedica

import android.app.Application
import android.content.Context

class AsistenciaMedicaApplication : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: AsistenciaMedicaApplication? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

}