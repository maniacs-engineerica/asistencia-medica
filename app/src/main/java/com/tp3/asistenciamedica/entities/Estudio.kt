package com.tp3.asistenciamedica.entities

import com.tp3.asistenciamedica.daos.EstudioDao
import com.tp3.asistenciamedica.daos.RecetaDao
import java.util.*

class Estudio() {

    companion object {
        var FIREBASE_COLLECTION: String = "estudios"
    }

    var idEstudio: String = ""
    var nombre: String = ""
    var resultado: String = ""
    lateinit var doctor: Usuario
    lateinit var paciente: Usuario
    var fecha: Date = Date()
    var ubicacionDeEstudio: String = ""
    var rutaDeImagenes: List<String> = listOf()


    constructor(estudio: EstudioDao) : this() {
        this.nombre = estudio.nombre
        this.resultado = estudio.resultado
        this.fecha = estudio.fecha
        this.ubicacionDeEstudio = estudio.ubicacionDeEstudio
        this.rutaDeImagenes = estudio.rutaDeImagenes
    }

}