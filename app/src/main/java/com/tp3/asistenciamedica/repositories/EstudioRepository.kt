package com.tp3.asistenciamedica.repositories

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tp3.asistenciamedica.daos.EstudioDao
import com.tp3.asistenciamedica.entities.Estudio
import com.tp3.asistenciamedica.entities.Receta
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

class EstudioRepository {

    private val db = Firebase.firestore

    suspend fun findEstudioById(id: String): Estudio? {

        val document = db.collection(Estudio.FIREBASE_COLLECTION).document(id)
            .get().await()

        return document.toEstudio()
    }

    suspend fun findEstudiosByProfesionalId(id: String): List<Estudio> {

        val documents = db.collection(Estudio.FIREBASE_COLLECTION)
            .whereEqualTo("doctorId", id)
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

    suspend fun create(estudio: EstudioDao) {
        db.collection(Estudio.FIREBASE_COLLECTION).add(estudio).await()
    }

}

private fun QuerySnapshot.toEstudios(): List<Estudio> {
    return mapNotNull { it.toEstudio() }
}

private fun DocumentSnapshot.toEstudio(): Estudio? {
    val dao = toObject(EstudioDao::class.java) ?: return null
    val estudio = Estudio(dao)

    estudio.idEstudio = id

    val userDb = UsuarioRepository()

    if (dao.doctorId.isNotEmpty()) {
        runBlocking { userDb.findUserById(dao.doctorId) }?.let {
            estudio.doctor = it
        }
    }

    if (dao.pacienteId.isNotEmpty()) {
        runBlocking { userDb.findUserById(dao.pacienteId) }?.let {
            estudio.paciente = it
        }
    }
    return estudio
}