package com.tp3.asistenciamedica.entities

class Usuario(
    val nombre: String,
    val apellido: String,
    val email: String,
    val contrasena: String,
    val dni: String,
    val telefono: String
) {

    constructor() : this("", "", "", "", "", "")
}