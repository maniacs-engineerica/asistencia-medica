package com.tp3.asistenciamedica.repositories

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tp3.asistenciamedica.daos.EstudioDao
import com.tp3.asistenciamedica.daos.RecetaDao
import com.tp3.asistenciamedica.daos.TurnoDao
import com.tp3.asistenciamedica.entities.Estudio
import com.tp3.asistenciamedica.entities.Receta
import com.tp3.asistenciamedica.entities.Turno
import kotlinx.coroutines.tasks.await

class RecetaRepository {

    private val db = Firebase.firestore
    private val userDb = UsuarioRepository()


    public suspend fun findRecetaByProfesionalId(id: String): List<Receta> {



        var documents = db.collection(Receta.FIREBASE_COLLECTION)
            .whereEqualTo("profesionalId", id)
            .get()
            .await()


        var daos = documents.toObjects(RecetaDao::class.java)


        return daos.map {
            convertDaoToReceta(it)
        }
    }

    public suspend fun findRecetaByPacientId(id: String): List<Receta> {



        var documents = db.collection(Receta.FIREBASE_COLLECTION)
            .whereEqualTo("pacienteId", id)
            .get()
            .await()


        var daos = documents.toObjects(RecetaDao::class.java)


        return daos.map {
            convertDaoToReceta(it)
        }
    }


    private suspend fun convertDaoToReceta(recetaDao: RecetaDao): Receta {

        lateinit var receta: Receta


        if (recetaDao != null) {

            receta = Receta(recetaDao)

            if (recetaDao.doctorId != null && recetaDao.doctorId != "") {

                receta.doctor = userDb.findUserById(recetaDao.doctorId)

            }

            if (recetaDao.pacienteId != null && recetaDao.pacienteId != "") {
                receta.paciente = userDb.findUserById(recetaDao.pacienteId)
            }


        }

        return receta

    }


}