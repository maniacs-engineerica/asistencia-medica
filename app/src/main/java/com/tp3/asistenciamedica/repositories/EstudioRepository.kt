package com.tp3.asistenciamedica.repositories

import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tp3.asistenciamedica.daos.EstudioDao
import com.tp3.asistenciamedica.daos.TurnoDao
import com.tp3.asistenciamedica.entities.Estudio
import com.tp3.asistenciamedica.entities.Turno
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

class EstudioRepository {

    private val db = Firebase.firestore

    suspend fun findEstudiosByProfesionalId(id: String): List<Estudio> {

        val documents = db.collection(Estudio.FIREBASE_COLLECTION)
            .whereEqualTo("profesionalId", id)
            .get()
            .await()

        return documents.toEstudios()
    }

    suspend fun findEstudiosByPacientId(id: String): List<Estudio> {

        val documents = db.collection(Estudio.FIREBASE_COLLECTION)
            .whereEqualTo("pacienteId", id)
            .get()
            .await()

        return documents.toEstudios()
    }

}

private fun QuerySnapshot.toEstudios(): List<Estudio> {
    val userDb = UsuarioRepository()
    return map { document ->
        val dao = document.toObject(EstudioDao::class.java)
        val estudio = Estudio(dao)

        estudio.idEstudio = document.id

        if (dao.doctorId.isNotEmpty()) {
            estudio.doctor = runBlocking { userDb.findUserById(dao.doctorId)  }
        }

        if (dao.pacienteId.isNotEmpty()) {
            estudio.paciente = runBlocking { userDb.findUserById(dao.pacienteId) }
        }
        estudio
    }
}
