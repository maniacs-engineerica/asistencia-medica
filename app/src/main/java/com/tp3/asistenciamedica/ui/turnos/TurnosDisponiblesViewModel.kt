package com.tp3.asistenciamedica.ui.turnos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


import com.tp3.asistenciamedica.entities.Turno

class TurnosDisponiblesViewModel : ViewModel() {
    private val _turnos = MutableLiveData<List<Turno>>()
    val turnos: LiveData<List<Turno>> = _turnos

    fun setTurnos(turnos: List<Turno>) {
        _turnos.value = turnos
    }


}