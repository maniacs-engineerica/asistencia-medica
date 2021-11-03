package com.tp3.asistenciamedica.ui.recetas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tp3.asistenciamedica.entities.Receta

class RecetaViewModel : ViewModel() {

    private val _receta = MutableLiveData<Receta>()
    val receta: LiveData<Receta> = _receta

    fun setReceta(receta: Receta){
        _receta.value = receta
    }


}