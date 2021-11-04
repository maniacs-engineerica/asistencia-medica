package com.tp3.asistenciamedica.ui.turnos

import androidx.navigation.fragment.findNavController
import com.tp3.asistenciamedica.entities.Turno
import com.tp3.asistenciamedica.entities.UsuarioTypeEnum
import com.tp3.asistenciamedica.repositories.TurnoRepository
import com.tp3.asistenciamedica.session.Session

class HistorialFragment : TurnosFragment() {

    override suspend fun onLoadTurnos(): List<Turno> {
        val usuario = Session.current()
        return TurnoRepository().findHistorialByPacienteId(usuario.id)
    }

    override fun onTurnoClick(turno: Turno) {
        findNavController().navigate(HistorialFragmentDirections.actionHistorialToTurno(turno.idTurno))
    }

}