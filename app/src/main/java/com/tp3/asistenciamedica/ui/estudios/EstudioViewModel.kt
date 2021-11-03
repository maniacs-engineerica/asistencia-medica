package com.tp3.asistenciamedica.ui.estudios

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tp3.asistenciamedica.entities.Estudio
import com.tp3.asistenciamedica.entities.Receta

class EstudioViewModel : ViewModel() {
    private val _estudio = MutableLiveData<Estudio>()
    val estudio: LiveData<Estudio> = _estudio

    fun setEstudio(estudio: Estudio) {
        _estudio.value = estudio
    }
}