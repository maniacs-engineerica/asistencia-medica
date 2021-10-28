package com.tp3.asistenciamedica.repositories

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tp3.asistenciamedica.daos.EstudioDao
import com.tp3.asistenciamedica.daos.RecetaDao
import com.tp3.asistenciamedica.daos.TurnoDao
import com.tp3.asistenciamedica.entities.Estudio
import com.tp3.asistenciamedica.entities.Receta
import com.tp3.asistenciamedica.entities.Turno
import com.tp3.asistenciamedica.entities.Usuario
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

class RecetaRepository {

    private val db = Firebase.firestore

    suspend fun findRecetaById(id: String): Receta? {

        val document = db.collection(Usuario.FIREBASE_COLLECTION).document(id)
            .get().await()

        return document.toReceta()
    }

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
    return mapNotNull { it.toReceta() }
}

private fun DocumentSnapshot.toReceta(): Receta? {
    val dao = toObject(RecetaDao::class.java) ?: return null
    val receta = Receta(dao)

    receta.idReceta = id

    val userDb = UsuarioRepository()

    if (dao.doctorId.isNotEmpty()) {
        runBlocking { userDb.findUserById(dao.doctorId) }?.let {
            receta.doctor = it
        }
    }

    if (dao.pacienteId.isNotEmpty()) {
        runBlocking { userDb.findUserById(dao.pacienteId) }?.let {
            receta.paciente = it
        }
    }
    return receta
}
