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
import com.tp3.asistenciamedica.entities.Usuario
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


    @SuppressLint("NewApi")
    suspend fun findTurnoByProfesionalId(profesional: Usuario): List<Turno> {

        val documents = db.collection(Turno.FIREBASE_COLLECTION)
            .whereEqualTo("doctorId", profesional.id)
            .get()
            .await()

        return documents.toTurnos(profesional).sortedBy { ZonedDateTime.parse(it.dateTime) }
    }

    @SuppressLint("NewApi")
    suspend fun findTurnosByEspecialidad(especialidad:String, state: TurnoStatusEnum): List<Turno> {
        val documents = db.collection(Turno.FIREBASE_COLLECTION)
            .whereEqualTo("specialization",especialidad)
            .whereEqualTo("state", state )
            .get()
            .await()

        return documents.toTurnos()
            //.filter { ZonedDateTime.parse(it.dateTime).isAfter(ZonedDateTime.now()) }
            .sortedBy { ZonedDateTime.parse(it.dateTime) }
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
            .whereEqualTo("state", TurnoStatusEnum.CERRADO )
            .get()
            .await()

        return documents.toTurnos().sortedBy { ZonedDateTime.parse(it.dateTime) }
    }


}

private fun QuerySnapshot.toTurnos(profesional: Usuario): List<Turno> {
    return mapNotNull { it.toTurno(profesional) }
}

private fun QuerySnapshot.toTurnos(): List<Turno> {
    val mapUser = mutableMapOf<String, Usuario>()
    return mapNotNull { it.toTurno(mapUser) }
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

private fun DocumentSnapshot.toTurno(mapUser: MutableMap<String, Usuario>): Turno? {
    val userDb = UsuarioRepository()

    val dao = toObject(TurnoDao::class.java) ?: return null

    val turno = Turno(dao)

    turno.idTurno = id

    if (dao.doctorId.isNotEmpty()) {

        if (mapUser.containsKey(dao.doctorId)) {
            turno.doctor = mapUser[dao.doctorId]!!
        }
        else {
            runBlocking { userDb.findUserById(dao.doctorId) }?.let {
                turno.doctor = it

                if (!mapUser.containsKey(dao.doctorId)) {
                    mapUser[dao.doctorId] = it
                }

            }

        }
    }

    if (dao.pacienteId.isNotEmpty()) {

        if (mapUser.containsKey(dao.pacienteId)) {
            turno.paciente = mapUser[dao.pacienteId]!!
        }
        else {
            runBlocking { userDb.findUserById(dao.pacienteId) }?.let {
                turno.paciente = it

                if (!mapUser.containsKey(dao.pacienteId)) {
                    mapUser[dao.pacienteId] = it
                }

            }
        }

    }

    return turno
}

private fun DocumentSnapshot.toTurno(profesional: Usuario): Turno? {
    val userDb = UsuarioRepository()

    val dao = toObject(TurnoDao::class.java) ?: return null

    val turno = Turno(dao)

    turno.idTurno = id

    turno.doctor = profesional

    if (dao.pacienteId.isNotEmpty()) {
        runBlocking { userDb.findUserById(dao.pacienteId) }?.let {
            turno.paciente = it
        }
    }

    return turno
}
