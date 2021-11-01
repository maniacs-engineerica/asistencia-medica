package com.tp3.asistenciamedica.daos

import java.util.*

class EstudioDao {

    var nombre: String = ""
    var resultado: String = ""
    var doctorId: String = ""
    var pacienteId: String = ""
    var fecha: Date = Date()
    var ubicacionDeEstudio: String = ""
    var rutaDeImagenes: MutableList<String> = mutableListOf<String>()

}