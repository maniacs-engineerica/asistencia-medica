package com.tp3.asistenciamedica.ui.doctor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tp3.asistenciamedica.entities.Estudio

class PacienteEstudiosViewModel : ViewModel() {
    private val _estudios = MutableLiveData<List<Estudio>>()
    val estudios: LiveData<List<Estudio>> = _estudios

    fun setEstudios(estudios: List<Estudio>){
        _estudios.value = estudios
    }
}