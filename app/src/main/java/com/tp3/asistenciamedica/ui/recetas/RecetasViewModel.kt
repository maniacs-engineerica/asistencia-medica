package com.tp3.asistenciamedica.ui.recetas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tp3.asistenciamedica.entities.Estudio
import com.tp3.asistenciamedica.entities.Receta
import com.tp3.asistenciamedica.entities.Turno

class RecetasViewModel : ViewModel() {

    private val _recetas = MutableLiveData<List<Receta>>().apply {
        value = listOf(
            Receta("Alberto Cormillot", "Hipoglos 200mg"),
            Receta("Mario Socolinsky", "Ibupirac Jarabe 4%"),
            Receta("Nelson Castro", "Amoxidal Duo 30 comprimidos"),
            Receta("Daniel Lopez Rosetti", "Sertal 10 comprimidos")
        )
    }
    val recetas: LiveData<List<Receta>> = _recetas
}