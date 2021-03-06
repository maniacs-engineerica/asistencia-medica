package com.tp3.asistenciamedica.entities

import com.tp3.asistenciamedica.daos.TurnoDao

class Turno() {

    companion object {
        var FIREBASE_COLLECTION: String = "turnos"
    }

    constructor(dao: TurnoDao) : this() {
        this.dateTime = dao.dateTime
        this.state = dao.state
        this.detail = dao.detail
        this.specialization = TurnoEspecialidadEnum.findByCode(dao.specialization)
    }


    var idTurno: String = ""
    var dateTime: String = ""
    var state: TurnoStatusEnum = TurnoStatusEnum.DISPONIBLE
    lateinit var doctor: Usuario
    var paciente: Usuario? = null
    var detail: String = ""
    lateinit var specialization: TurnoEspecialidadEnum


    fun withId(id: String): Turno {
        this.idTurno
        return this
    }

    fun withDate(date: String): Turno {
        this.dateTime = date
        return this
    }

    fun withState(state: TurnoStatusEnum): Turno {
        this.state = state
        return this
    }

    fun withDoctorId(user: Usuario): Turno {
        this.doctor = user
        return this
    }

    fun withPacientId(user: Usuario): Turno {
        this.paciente = user
        return this
    }

    fun withDetail(detail: String): Turno {
        this.detail = detail
        return this
    }

    fun withSpecialization(specialization: TurnoEspecialidadEnum): Turno {
        this.specialization = specialization
        return this
    }


}