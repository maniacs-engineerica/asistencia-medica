package com.tp3.asistenciamedica.entities

class Usuario(
    val nombre: String,
    val apellido: String,
    val email: String,
    val contrasena: String,
    val dni: String,
    val telefono: String,
    val tipo: UsuarioTypeEnum
) {

    var id: String = ""

    var nombreCompleto: String = ""
        get() = "$nombre $apellido"

    companion object {
        var FIREBASE_COLLECTION: String = "usuarios"
    }

    constructor() : this("","", "", "", "", "", UsuarioTypeEnum.PACIENTE)

    override fun toString(): String {
        return nombreCompleto
    }
}