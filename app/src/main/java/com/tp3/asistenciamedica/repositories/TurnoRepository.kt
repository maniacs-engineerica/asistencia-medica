package com.tp3.asistenciamedica.repositories

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tp3.asistenciamedica.daos.EstudioDao
import com.tp3.asistenciamedica.daos.TurnoDao
import com.tp3.asistenciamedica.entities.Estudio
import com.tp3.asistenciamedica.entities.Turno
import com.tp3.asistenciamedica.entities.TurnoStatusEnum
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await


class TurnoRepository {

    private val db = Firebase.firestore

    suspend fun findTurnoByDocumentId(id: String): Turno? {

        val document = db.collection(Turno.FIREBASE_COLLECTION).document(id)
            .get().await()

        return document.toTurno()
    }

    suspend fun findTurnosByPacienteId(id: String): List<Turno> {

        val documents = db.collection(Turno.FIREBASE_COLLECTION)
            .whereEqualTo("pacienteId", id)
            .get()
            .await()

        return documents.toTurnos()
    }

    suspend fun findTurnoByState(state: TurnoStatusEnum): List<Turno> {

        val documents = db.collection(Turno.FIREBASE_COLLECTION)
            .whereEqualTo("state", state)
            .get()
            .await()

        return documents.toTurnos()
    }


    suspend fun findTurnoByProfesionalId(id: String): List<Turno> {

        val documents = db.collection(Turno.FIREBASE_COLLECTION)
            .whereEqualTo("profesionalId", id)
            .get()
            .await()


        return documents.toTurnos()
    }

    suspend fun findTurnosByEspecialidad(especialidad:String): List<Turno> {
        val documents = db.collection(Turno.FIREBASE_COLLECTION)
            .whereEqualTo("specialization",especialidad)
            .get()
            .await()

        return documents.toTurnos()
    }

    suspend fun saveTurnos(turnos: List<Turno>) {
        turnos.forEach { saveTurno(it) }
    }

    suspend fun saveTurno(turno: Turno) {
        val dao = TurnoDao(turno);

        if (dao.idTurno == "") {
            db.collection(Turno.FIREBASE_COLLECTION)
                .add(dao)
                .await()
        }
        else {
            db.collection(Turno.FIREBASE_COLLECTION)
                .document(dao.idTurno)
                .set(dao)
                .await()
        }
    }

    suspend fun saveTurnosDao(turnos: List<TurnoDao>) {
        turnos.forEach { saveTurnoDao(it) }
    }

    suspend fun saveTurnoDao(dao: TurnoDao) {

        if (dao.idTurno == "") {
            db.collection(Turno.FIREBASE_COLLECTION)
                .add(dao)
                .addOnSuccessListener { documentReference ->
                    //Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    //Log.w(TAG, "Error adding document", e)
                }
        }
        else {
            db.collection(Turno.FIREBASE_COLLECTION)
                .document(dao.idTurno)
                .set(dao)
        }
    }


}

private fun QuerySnapshot.toTurnos(): List<Turno> {
    return mapNotNull { it.toTurno() }
}

private fun DocumentSnapshot.toTurno(): Turno? {
    val userDb = UsuarioRepository()

    val dao = toObject(TurnoDao::class.java) ?: return null

    val turno = Turno(dao)

    turno.idTurno = id

    runBlocking {
        launch {
            if (dao.doctorId.isNotEmpty()) {
                turno.doctor = runBlocking { userDb.findUserById(dao.doctorId)  }
            }
        }
        launch {
            if (dao.pacienteId.isNotEmpty()) {
                turno.paciente = runBlocking { userDb.findUserById(dao.pacienteId) }
            }
        }
    }

    return turno
}
