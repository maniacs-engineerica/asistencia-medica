package com.tp3.asistenciamedica.ui.recetas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tp3.asistenciamedica.entities.Estudio
import com.tp3.asistenciamedica.entities.Turno

class TurnosViewModel : ViewModel() {

    private val _turnos = MutableLiveData<List<Turno>>()
    val turnos: LiveData<List<Turno>> = _turnos

    fun setTurnos(turnos: List<Turno>) {
        _turnos.value = turnos
    }
}