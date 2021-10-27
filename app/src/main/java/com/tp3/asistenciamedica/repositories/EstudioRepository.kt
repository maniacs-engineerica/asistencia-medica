package com.tp3.asistenciamedica.repositories

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tp3.asistenciamedica.daos.EstudioDao
import com.tp3.asistenciamedica.daos.TurnoDao
import com.tp3.asistenciamedica.entities.Estudio
import com.tp3.asistenciamedica.entities.Turno
import kotlinx.coroutines.tasks.await

class EstudioRepository {

    private val db = Firebase.firestore
    private val userDb = UsuarioRepository()

    suspend fun findEstudiosByProfesionalId(id: String): List<Estudio> {

        val documents = db.collection(Estudio.FIREBASE_COLLECTION)
            .whereEqualTo("profesionalId", id)
            .get()
            .await()


        val daos = documents.toObjects(EstudioDao::class.java)

        return daos.map {
            convertDaoToEstudio(it)
        }
    }

    suspend fun findEstudiosByPacientId(id: String): List<Estudio> {

        val documents = db.collection(Estudio.FIREBASE_COLLECTION)
            .whereEqualTo("pacienteId", id)
            .get()
            .await()


        val daos = documents.toObjects(EstudioDao::class.java)

        return daos.map {
            convertDaoToEstudio(it)
        }
    }


    private suspend fun convertDaoToEstudio(estudioDao: EstudioDao): Estudio {

        lateinit var estudio: Estudio


        if (estudioDao != null) {

            estudio = Estudio(estudioDao)

            if (estudioDao.doctorId != null && estudioDao.doctorId != "") {

                estudio.doctor = userDb.findUserById(estudioDao.doctorId)

            }

            if (estudioDao.pacienteId != null && estudioDao.pacienteId != "") {
                estudio.paciente = userDb.findUserById(estudioDao.pacienteId)
            }


        }

        return estudio

    }


}