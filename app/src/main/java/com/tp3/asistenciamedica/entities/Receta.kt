package com.tp3.asistenciamedica.entities

import com.tp3.asistenciamedica.daos.RecetaDao

class Receta() {

    companion object {
        var FIREBASE_COLLECTION: String = "recetas"
    }

    var idReceta: String = ""
    var doctor: Usuario? = null
    var paciente: Usuario? = null
    var fecha: String = ""
    var descripcion: String = ""
    var ubicacionDeReceta: String = ""
    var rutaDeImagenes: MutableList<String> = mutableListOf<String>()


    constructor(receta: RecetaDao) : this() {
        this.fecha = receta.fecha
        this.descripcion = receta.descripcion
        this.ubicacionDeReceta = receta.ubicacionDeReceta
        this.rutaDeImagenes = receta.rutaDeImagenes
    }

}