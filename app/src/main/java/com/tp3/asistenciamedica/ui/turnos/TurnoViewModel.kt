package com.tp3.asistenciamedica.ui.turnos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tp3.asistenciamedica.entities.Estudio
import com.tp3.asistenciamedica.entities.Turno

class TurnoViewModel : ViewModel() {

    private val _turno = MutableLiveData<Turno>()
    val turno: LiveData<Turno> = _turno

    fun setTurno(turno: Turno){
        _turno.value = turno
    }

}