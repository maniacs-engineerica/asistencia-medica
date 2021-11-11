package com.tp3.asistenciamedica.ui.recetas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tp3.asistenciamedica.entities.Receta
import com.tp3.asistenciamedica.entities.Resource

class RecetaViewModel : ViewModel() {

    private val _receta = MutableLiveData<Receta>()
    val receta: LiveData<Receta> = _receta
    private val _resources = MutableLiveData<List<Resource>>().apply {
        value = listOf()
    }
    val resources: LiveData<List<Resource>> = _resources

    fun setReceta(receta: Receta){
        _receta.value = receta
        setResources(receta.rutaDeImagenes.map { Resource(it) })
    }

    fun setResources(resources: List<Resource>) {
        _resources.value = resources
    }


}