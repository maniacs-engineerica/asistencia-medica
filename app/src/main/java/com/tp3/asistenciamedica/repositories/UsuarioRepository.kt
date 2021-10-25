package com.tp3.asistenciamedica.repositories

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tp3.asistenciamedica.entities.Turno
import com.tp3.asistenciamedica.entities.Usuario
import kotlinx.coroutines.tasks.await

class UsuarioRepository {

    private val db = Firebase.firestore


    public suspend fun findUserById(id: String): Usuario? {

        var document = db.collection(Usuario.FIREBASE_COLLECTION).document(id)
            .get().await()

        val user = document.toObject(Usuario::class.java)


        return user
    }
}