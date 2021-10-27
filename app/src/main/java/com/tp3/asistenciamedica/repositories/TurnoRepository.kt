package com.tp3.asistenciamedica.repositories

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tp3.asistenciamedica.daos.TurnoDao
import com.tp3.asistenciamedica.entities.Turno
import com.tp3.asistenciamedica.entities.TurnoStatusEnum
import kotlinx.coroutines.tasks.await


class TurnoRepository {

    private val db = Firebase.firestore
    private val userDb = UsuarioRepository()

    suspend fun findTurnoByDocumentId(id: String): Turno? {

        val document = db.collection(Turno.FIREBASE_COLLECTION).document(id)
            .get().await()

        val dao = document.toObject(TurnoDao::class.java)

        if (dao != null) return convertDaoToTurno(dao)
        return null
    }

    suspend fun findTurnosByPacienteId(id: String): List<Turno> {

        val documents = db.collection(Turno.FIREBASE_COLLECTION)
            .whereEqualTo("pacienteId", id)
            .get()
            .await()

        val daos = documents.toObjects(TurnoDao::class.java)

        return daos.map {
            convertDaoToTurno(it)
        }
    }

    suspend fun findTurnoByState(state: TurnoStatusEnum): List<Turno> {

        val documents = db.collection(Turno.FIREBASE_COLLECTION)
            .whereEqualTo("state", state)
            .get()
            .await()

        val daos = documents.toObjects(TurnoDao::class.java)


        return daos.map {
            convertDaoToTurno(it)
        }

    }


    suspend fun findTurnoByProfesionalId(id: String): List<Turno> {

        val documents = db.collection(Turno.FIREBASE_COLLECTION)
            .whereEqualTo("profesionalId", id)
            .get()
            .await()


        var daos = documents.toObjects(TurnoDao::class.java)

        var mutableTurnos = mutableListOf<Turno>()


        return daos.map {
            convertDaoToTurno(it)
        }
    }

    private suspend fun convertDaoToTurno(turnoDao: TurnoDao): Turno {

        lateinit var turno: Turno


        if (turnoDao != null) {

            turno = Turno(turnoDao)

            if (turnoDao.doctorId != null && turnoDao.doctorId != "") {

                turno.doctor = userDb.findUserById(turnoDao.doctorId)

            }

            if (turnoDao.pacienteId != null && turnoDao.pacienteId != "") {
                turno.paciente = userDb.findUserById(turnoDao.pacienteId)
            }


        }

        return turno

    }



}