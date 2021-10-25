package com.tp3.asistenciamedica.daos

import com.tp3.asistenciamedica.entities.TurnoStatusEnum
import com.tp3.asistenciamedica.entities.Usuario

class TurnoDao {

    public var idTurno: String = ""
    public var dateTime: String = ""
    public var state: TurnoStatusEnum = TurnoStatusEnum.DISPONIBLE
    public var doctorId: String = ""
    public var pacienteId: String = ""
    public var detail: String = ""
    public var specialization: String = ""

}