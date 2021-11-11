package com.tp3.asistenciamedica.daos

import java.util.*

class RecetaDao {

    var doctorId: String = ""
    var pacienteId: String = ""
    var fecha: Date = Date()
    var descripcion: String = ""
    var ubicacionDeReceta: String = ""
    var rutaDeImagenes: List<String> = listOf()

}