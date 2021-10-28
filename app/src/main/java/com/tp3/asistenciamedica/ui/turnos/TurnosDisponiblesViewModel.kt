package com.tp3.asistenciamedica.ui.turnos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


import com.tp3.asistenciamedica.entities.Turno

class TurnosDisponiblesViewModel : ViewModel() {
    private val db = Firebase.firestore

    var turnosDisp: List<Turno> = obtenerTurnosDisp()


    fun obtenerTurnosDisp(): List<Turno> {

        db.collection("turnos_disponibles")
            .get()
            .addOnSuccessListener {
                    documents ->
                for(document in documents) {
                    var turno= document.toObject(Turno::class.java)
                   turnosDisp= listOf(turno)
                }
            }
        return turnosDisp
    }

    private val _turnos = MutableLiveData<List<Turno>>().apply {


        value = turnosDisp
            //listOf(

            /*Turno("Alberto Cormillot"),
            Turno("Mario Socolinsky"),
            Turno("Nelson Castro"),
            Turno("Daniel Lopez Rosetti")*/
       // )
    }
    val turnos: LiveData<List<Turno>> = _turnos


}