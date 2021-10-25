package com.tp3.asistenciamedica.entities

import com.tp3.asistenciamedica.daos.RecetaDao

class Receta() {

    companion object {
        public var FIREBASE_COLLECTION: String = "recetas"
    }

    public var idReceta: String = ""
    public var doctor: Usuario? = null
    public var paciente: Usuario? = null
    public var fecha: String = ""
    public var ubicacionDeReceta: String = ""
    public var rutaDeImagenes: MutableList<String> = mutableListOf<String>()


    constructor(receta: RecetaDao) : this() {
        this.idReceta = receta.idReceta
        this.fecha = receta.fecha
        this.ubicacionDeReceta = receta.ubicacionDeReceta
        this.rutaDeImagenes = receta.rutaDeImagenes
    }

}