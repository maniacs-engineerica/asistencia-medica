package com.tp3.asistenciamedica.repositories

import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tp3.asistenciamedica.daos.EstudioDao
import com.tp3.asistenciamedica.daos.RecetaDao
import com.tp3.asistenciamedica.daos.TurnoDao
import com.tp3.asistenciamedica.entities.Estudio
import com.tp3.asistenciamedica.entities.Receta
import com.tp3.asistenciamedica.entities.Turno
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

class RecetaRepository {

    private val db = Firebase.firestore

    suspend fun findRecetaByProfesionalId(id: String): List<Receta> {

        val documents = db.collection(Receta.FIREBASE_COLLECTION)
            .whereEqualTo("profesionalId", id)
            .get()
            .await()

        return documents.toRecetas()
    }

    suspend fun findRecetaByPacientId(id: String): List<Receta> {

        val documents = db.collection(Receta.FIREBASE_COLLECTION)
            .whereEqualTo("pacienteId", id)
            .get()
            .await()

        return documents.toRecetas()
    }
}

private fun QuerySnapshot.toRecetas(): List<Receta> {
    val userDb = UsuarioRepository()
    return map { document ->
        val dao = document.toObject(RecetaDao::class.java)
        val receta = Receta(dao)

        receta.idReceta = document.id

        if (dao.doctorId.isNotEmpty()) {
            receta.doctor = runBlocking { userDb.findUserById(dao.doctorId)  }
        }

        if (dao.pacienteId.isNotEmpty()) {
            receta.paciente = runBlocking { userDb.findUserById(dao.pacienteId) }
        }
        receta
    }
}
