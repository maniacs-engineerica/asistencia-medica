package com.tp3.asistenciamedica.ui.recetas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tp3.asistenciamedica.entities.Estudio
import com.tp3.asistenciamedica.entities.Turno

class TurnosViewModel : ViewModel() {

    private val _turnos = MutableLiveData<List<Turno>>().apply {

        // TODO: Fix this list
        value = listOf(/*
            Turno("Alberto Cormillot"),
            Turno("Mario Socolinsky"),
            Turno("Nelson Castro"),
            Turno("Daniel Lopez Rosetti")*/
        )
    }
    val turnos: LiveData<List<Turno>> = _turnos
}