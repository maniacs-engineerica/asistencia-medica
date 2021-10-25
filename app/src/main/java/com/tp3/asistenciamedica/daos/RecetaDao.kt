package com.tp3.asistenciamedica.daos

import com.tp3.asistenciamedica.entities.Usuario

class RecetaDao {

    public var idReceta: String = ""
    public var doctorId: String = ""
    public var pacienteId: String = ""
    public var fecha: String = ""
    public var ubicacionDeReceta: String = ""
    public var rutaDeImagenes: MutableList<String> = mutableListOf<String>()

}