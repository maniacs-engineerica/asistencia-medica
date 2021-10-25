package com.tp3.asistenciamedica.entities

class Usuario(
    val id: String,
    val nombre: String,
    val apellido: String,
    val email: String,
    val contrasena: String,
    val dni: String,
    val telefono: String,
    val tipo: UsuarioTypeEnum
) {

    companion object {
        public var FIREBASE_COLLECTION: String = "usuarios"
    }

    constructor() : this("","", "", "", "", "", "", UsuarioTypeEnum.PACIENTE)
}