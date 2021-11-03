package com.tp3.asistenciamedica.ui.turnos

import androidx.navigation.fragment.findNavController
import com.tp3.asistenciamedica.entities.Turno

class HistorialFragment : TurnosFragment() {

    override fun onTurnoClick(turno: Turno) {
        findNavController().navigate(HistorialFragmentDirections.actionHistorialToTurno(turno.idTurno))
    }

}