package com.tp3.asistenciamedica.ui.estudios

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tp3.asistenciamedica.entities.Estudio

class EstudiosViewModel : ViewModel() {

    private val _estudios = MutableLiveData<List<Estudio>>().apply {
        value = listOf(
            Estudio("Radiografía"),
            Estudio("Tomografía"),
            Estudio("Ergometría"),
            Estudio("Ecografía")
        )
    }
    val estudios: LiveData<List<Estudio>> = _estudios
}