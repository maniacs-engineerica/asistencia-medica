package com.tp3.asistenciamedica.ui.recetas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tp3.asistenciamedica.entities.Estudio
import com.tp3.asistenciamedica.entities.Receta
import com.tp3.asistenciamedica.entities.Turno

class RecetasViewModel : ViewModel() {

    private val _recetas = MutableLiveData<List<Receta>>()
    val recetas: LiveData<List<Receta>> = _recetas

    fun setRecetas(recetas: List<Receta>) {
        _recetas.value = recetas
    }
}