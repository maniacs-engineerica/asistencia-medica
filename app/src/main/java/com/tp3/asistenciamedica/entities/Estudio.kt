package com.tp3.asistenciamedica.entities

class Estudio() {

    public var profesional: Usuario? = null
    public var paciente: Usuario? = null
    public val ruta: String = ""
    public val fecha: String = ""
    public val ubicacionDeReceta: String = ""
    public val rutaDeImagenes: MutableList<String> = mutableListOf<String>()

}