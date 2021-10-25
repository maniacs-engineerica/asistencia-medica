package com.tp3.asistenciamedica.repositories

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tp3.asistenciamedica.daos.TurnoDao
import com.tp3.asistenciamedica.entities.Turno
import kotlinx.coroutines.tasks.await


class TurnoRepository {


    private val db = Firebase.firestore



    suspend fun findTurnoByDocumentId(id: String): TurnoDao? {

        var document = db.collection(Turno.FIREBASE_COLLECTION).document(id)
            .get().await()

        return document.toObject(TurnoDao::class.java)
    }

    suspend fun findTurnoByProfesionalId(id: String): MutableList<TurnoDao> {

        var document = db.collection(Turno.FIREBASE_COLLECTION)
            .whereEqualTo("profesionalId", id)
            .get()
            .await()

        return document.toObjects(TurnoDao::class.java)
    }


}