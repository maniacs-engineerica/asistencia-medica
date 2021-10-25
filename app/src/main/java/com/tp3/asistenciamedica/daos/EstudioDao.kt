package com.tp3.asistenciamedica.daos

import com.tp3.asistenciamedica.entities.Usuario

class EstudioDao {

    public var idEstudio: String = ""
    public var doctorId: String = ""
    public var pacienteId: String = ""
    public var fecha: String = ""
    public var ubicacionDeEstudio: String = ""
    public var rutaDeImagenes: MutableList<String> = mutableListOf<String>()

}