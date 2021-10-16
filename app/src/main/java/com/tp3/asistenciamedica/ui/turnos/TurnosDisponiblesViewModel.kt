package com.tp3.asistenciamedica.ui.turnos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.tp3.asistenciamedica.entities.Turno

class TurnosDisponiblesViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    private val _turnos = MutableLiveData<List<Turno>>().apply {
        value = listOf(
            Turno("Radiografía"),
            Turno("Tomografía"),
            Turno("Ergometría"),
            Turno("Ecografía")
        )
    }
    val turnos: LiveData<List<Turno>> = _turnos

    


}