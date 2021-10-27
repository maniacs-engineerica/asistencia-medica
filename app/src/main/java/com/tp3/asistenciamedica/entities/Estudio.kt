package com.tp3.asistenciamedica.entities

import com.tp3.asistenciamedica.daos.EstudioDao
import com.tp3.asistenciamedica.daos.RecetaDao

class Estudio() {

    companion object {
        var FIREBASE_COLLECTION: String = "estudios"
    }

    var idEstudio: String = ""
    var nombre: String = ""
    var doctor: Usuario? = null
    var paciente: Usuario? = null
    var fecha: String = ""
    var ubicacionDeEstudio: String = ""
    var rutaDeImagenes: MutableList<String> = mutableListOf<String>()


    constructor(estudio: EstudioDao) : this() {
        this.idEstudio = estudio.idEstudio
        this.nombre = estudio.nombre
        this.fecha = estudio.fecha
        this.ubicacionDeEstudio = estudio.ubicacionDeEstudio
        this.rutaDeImagenes = estudio.rutaDeImagenes
    }

}