package com.tp3.asistenciamedica.entities

import java.time.ZonedDateTime

class Turno(val profesional: String) {

    public lateinit var dateTime: ZonedDateTime

    constructor() : this("")

    public fun withDate(date: ZonedDateTime): Turno {
        this.dateTime = date
        return this
    }

}