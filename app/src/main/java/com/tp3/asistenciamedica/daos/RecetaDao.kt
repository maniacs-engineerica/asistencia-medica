package com.tp3.asistenciamedica.daos

class RecetaDao {

    var doctorId: String = ""
    var pacienteId: String = ""
    var fecha: String = ""
    var descripcion: String = ""
    var ubicacionDeReceta: String = ""
    var rutaDeImagenes: MutableList<String> = mutableListOf<String>()

}