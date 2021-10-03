package com.tp3.asistenciamedica.ui.recetas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TurnosViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is turnos Fragment"
    }
    val text: LiveData<String> = _text
}