package com.tp3.asistenciamedica.repositories

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import java.time.ZonedDateTime
import java.util.*


class TurnoRepository {

    private val db = Firebase.firestore

    suspend fun findTurnoById(id: String): Turno? {

        val document = db.collection(Turno.FIREBASE_COLLECTION).document(id)
            .get().await()

        return document.toTurno()
    }

    @SuppressLint("NewApi")
    suspend fun findTurnosByPacienteId(id: String): List<Turno> {

        val documents = db.collection(Turno.FIREBASE_COLLECTION)
            .whereEqualTo("pacienteId", id)
            .get()
            .await()

        val turnos = documents.toTurnos().sortedBy { ZonedDateTime.parse(it.dateTime) }
        return turnos.filter { ZonedDateTime.parse(it.dateTime).isAfter(ZonedDateTime.now()) }
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

        return documents.toTurnos().sortedBy { ZonedDateTime.parse(it.dateTime) }
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
        } else {
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
        } else {
            db.collection(Turno.FIREBASE_COLLECTION)
                .document(dao.idTurno)
                .set(dao)
        }
    }

    @SuppressLint("NewApi")
    suspend fun findHistorialByPacienteId(id: String): List<Turno> {
        val documents = db.collection(Turno.FIREBASE_COLLECTION)
            .whereEqualTo("pacienteId", id)
            .get()
            .await()

        val turnos = documents.toTurnos().sortedBy { ZonedDateTime.parse(it.dateTime) }
        return turnos.filter { ZonedDateTime.parse(it.dateTime).isBefore(ZonedDateTime.now()) }
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

    if (dao.doctorId.isNotEmpty()) {
        runBlocking { userDb.findUserById(dao.doctorId) }?.let {
            turno.doctor = it
        }
    }

    if (dao.pacienteId.isNotEmpty()) {
        runBlocking { userDb.findUserById(dao.pacienteId) }?.let {
            turno.paciente = it
        }
    }

    return turno
}
