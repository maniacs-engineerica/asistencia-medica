package com.tp3.asistenciamedica.repositories

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tp3.asistenciamedica.entities.Turno
import com.tp3.asistenciamedica.entities.Usuario
import kotlinx.coroutines.tasks.await

class UsuarioRepository {

    private val db = Firebase.firestore


    suspend fun findUserById(id: String): Usuario? {

        var document = db.collection(Usuario.FIREBASE_COLLECTION).document(id)
            .get().await()

        val user = document.toObject(Usuario::class.java)


        return user
    }

    suspend fun findUserByCredentials(email: String, password: String): Usuario? {

        val document = db.collection(Usuario.FIREBASE_COLLECTION)
            .whereEqualTo("email", email)
            .whereEqualTo("contrasena", password)
            .get()
            .await()

        if (document.isEmpty){
            return null
        }

        return document.toObjects(Usuario::class.java).first()
    }

    suspend fun userExists(email: String): Boolean {
        val document = db.collection(Usuario.FIREBASE_COLLECTION)
            .whereEqualTo("email", email)
            .get()
            .await()

        return !document.isEmpty
    }

    suspend fun create(usuario: Usuario) {
        db.collection(Usuario.FIREBASE_COLLECTION).add(usuario).await()
    }
}