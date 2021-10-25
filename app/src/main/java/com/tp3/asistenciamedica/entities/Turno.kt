package com.tp3.asistenciamedica.entities

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.tp3.asistenciamedica.daos.TurnoDao
import java.time.ZonedDateTime

class Turno() {

    companion object {
        public var FIREBASE_COLLECTION: String = "turnos"
    }

    constructor(dao: TurnoDao) : this() {
        this.idTurno = dao.idTurno
        this.dateTime = dao.dateTime
        this.state =  dao.state
        this.detail = dao.detail
        this.specialization = this.specialization
    }


    public var idTurno: String = ""
    public var dateTime: String = ""
    public var state: TurnoStatusEnum = TurnoStatusEnum.DISPONIBLE
    public var doctor: Usuario? = null
    public var paciente: Usuario? = null
    public var detail: String = ""
    public var specialization: String = ""


    public fun withId(id: String): Turno {
        this.idTurno
        return this
    }

    public fun withDate(date: String): Turno {
        this.dateTime = date
        return this
    }

    public fun withState(state: TurnoStatusEnum): Turno {
        this.state = state
        return this
    }

    public fun withDoctorId(user: Usuario): Turno {
        this.doctor = user
        return this
    }

    public fun withPacientId(user: Usuario): Turno {
        this.paciente = user
        return this
    }

    public fun withDetail(detail: String): Turno {
        this.detail = detail
        return this
    }

    public fun withSpecialization(specialization: String): Turno {
        this.specialization = specialization
        return this
    }


}