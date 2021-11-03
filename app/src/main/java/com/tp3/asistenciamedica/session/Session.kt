package com.tp3.asistenciamedica.session

import android.content.Context.MODE_PRIVATE
import com.tp3.asistenciamedica.AsistenciaMedicaApplication
import com.tp3.asistenciamedica.entities.Usuario
import java.lang.RuntimeException

object Session {

    private var usuario: Usuario? = null

    val isValid: Boolean
        get() = storage.contains("user")

    val storage = AsistenciaMedicaApplication.applicationContext()
        .getSharedPreferences("Session", MODE_PRIVATE)

    fun login(usuario: Usuario) {
        this.usuario = usuario
        storage.edit().putString("user", usuario.id).apply()
    }

    fun current(): Usuario {
        if (!isValid){
            throw RuntimeException("User not logged")
        }
        if (usuario == null){
            usuario = Usuario()
            usuario?.id = storage.getString("user", "")!!
        }
        return usuario!!
    }

    fun logout() {
        this.usuario = null
        storage.edit().remove("user").apply()
    }

}