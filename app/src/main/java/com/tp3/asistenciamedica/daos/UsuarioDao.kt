package com.tp3.asistenciamedica.daos

import com.tp3.asistenciamedica.entities.UsuarioTypeEnum

class UsuarioDao {

    var nombre: String = ""
    var apellido: String = ""
    var email: String = ""
    var contrasena: String = ""
    var dni: String = ""
    var telefono: String = ""
    var tipo: UsuarioTypeEnum = UsuarioTypeEnum.PACIENTE

}