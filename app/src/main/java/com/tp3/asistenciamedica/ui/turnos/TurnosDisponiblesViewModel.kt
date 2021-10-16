package com.tp3.asistenciamedica.ui.turnos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.tp3.asistenciamedica.entities.Turno

class TurnosDisponiblesViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    private val _turnos: MutableList<Turno> = mutableListOf()

    fun getTurnosDisp(): MutableList<Turno> {

            _turnos.add(Turno("Radiografía"))
            _turnos.add(Turno("Tomografía"))
            _turnos.add(Turno("Ergometría"))
            _turnos.add(Turno("Ecografía"))
        return _turnos
    }





}