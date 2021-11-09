package com.tp3.asistenciamedica.ui.estudios

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tp3.asistenciamedica.entities.Estudio
import com.tp3.asistenciamedica.entities.Receta
import com.tp3.asistenciamedica.entities.Resource

class EstudioViewModel : ViewModel() {
    private val _estudio = MutableLiveData<Estudio>()
    val estudio: LiveData<Estudio> = _estudio

    private val _resources = MutableLiveData<List<Resource>>().apply {
       value = listOf()
    }
    val resources: LiveData<List<Resource>> = _resources

    fun setEstudio(estudio: Estudio) {
        _estudio.value = estudio
        setResources(estudio.rutaDeImagenes.map { Resource(it) })
    }

    fun setResources(resources: List<Resource>) {
        _resources.value = resources
    }
}