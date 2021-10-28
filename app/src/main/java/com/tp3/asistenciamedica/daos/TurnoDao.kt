package com.tp3.asistenciamedica.daos

import com.tp3.asistenciamedica.entities.TurnoStatusEnum
import com.tp3.asistenciamedica.entities.Usuario

class TurnoDao {

    var dateTime: String = ""
    var state: TurnoStatusEnum = TurnoStatusEnum.DISPONIBLE
    var doctorId: String = ""
    var pacienteId: String = ""
    var detail: String = ""
    var specialization: String = ""

}