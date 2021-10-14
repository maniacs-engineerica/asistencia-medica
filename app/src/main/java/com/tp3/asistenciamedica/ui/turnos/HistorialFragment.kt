package com.tp3.asistenciamedica.ui.turnos

import androidx.navigation.fragment.findNavController

class HistorialFragment : TurnosFragment(){

    override fun onTurnoClick() {
        findNavController().navigate(HistorialFragmentDirections.actionHistorialToTurno())
    }

}