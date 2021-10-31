package com.tp3.asistenciamedica.daos

import com.tp3.asistenciamedica.entities.Turno
import com.tp3.asistenciamedica.entities.TurnoStatusEnum
import com.tp3.asistenciamedica.entities.Usuario

class TurnoDao {

    var idTurno: String = ""
    var dateTime: String = ""
    var state: TurnoStatusEnum = TurnoStatusEnum.DISPONIBLE
    var doctorId: String = ""
    var pacienteId: String = ""
    var detail: String = ""
    var specialization: String = ""

    constructor()

    constructor(turno: Turno) {
        this.idTurno = turno.idTurno
        this.dateTime = turno.dateTime
        this.state = turno.state
        this.doctorId = turno.doctor?.id ?: ""
        this.pacienteId = turno.paciente?.id ?: ""
        this.detail = turno.detail
        this.specialization = turno.specialization

    }


    fun toHashMapOf(): HashMap<String, String> {
        return hashMapOf(
            "idTurno" to this.idTurno,
            "dateTime" to this.dateTime,
            "state" to this.state.name,
            "doctorId" to this.doctorId,
            "pacienteId" to this.pacienteId,
            "detail" to this.detail,
            "specialization" to this.specialization
        )
    }

}