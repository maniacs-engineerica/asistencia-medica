package com.tp3.asistenciamedica.session

import android.content.Context.MODE_PRIVATE
import com.tp3.asistenciamedica.AsistenciaMedicaApplication
import com.tp3.asistenciamedica.entities.Usuario

object Session {

    private var usuario: Usuario? = null

    val isValid: Boolean
        get() = usuario != null

    val storage = AsistenciaMedicaApplication.applicationContext()
        .getSharedPreferences("Session", MODE_PRIVATE)

    fun login(usuario: Usuario) {
        this.usuario = usuario
        storage.edit().putString("user", usuario.id).apply()
    }

    fun current(): Usuario {
        return usuario!!
    }

    fun logout() {
        this.usuario = null
        storage.edit().remove("user").apply()
    }

}