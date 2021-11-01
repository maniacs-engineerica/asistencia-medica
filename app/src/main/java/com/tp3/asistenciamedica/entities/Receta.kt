package com.tp3.asistenciamedica.entities

import com.tp3.asistenciamedica.daos.RecetaDao
import java.util.*

class Receta() {

    companion object {
        var FIREBASE_COLLECTION: String = "recetas"
    }

    var idReceta: String = ""
    lateinit var doctor: Usuario
    lateinit var paciente: Usuario
    var fecha: Date = Date()
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