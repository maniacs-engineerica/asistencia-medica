package com.tp3.asistenciamedica.entities

import com.tp3.asistenciamedica.daos.EstudioDao
import com.tp3.asistenciamedica.daos.RecetaDao

class Estudio() {

    companion object {
        public var FIREBASE_COLLECTION: String = "estudios"
    }

    public var idEstudio: String = ""
    public var doctor: Usuario? = null
    public var paciente: Usuario? = null
    public var fecha: String = ""
    public var ubicacionDeEstudio: String = ""
    public var rutaDeImagenes: MutableList<String> = mutableListOf<String>()


    constructor(estudio: EstudioDao) : this() {
        this.idEstudio = estudio.idEstudio
        this.fecha = estudio.fecha
        this.ubicacionDeEstudio = estudio.ubicacionDeEstudio
        this.rutaDeImagenes = estudio.rutaDeImagenes
    }

}