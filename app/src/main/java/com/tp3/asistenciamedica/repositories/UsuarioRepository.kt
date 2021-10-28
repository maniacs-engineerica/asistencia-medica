package com.tp3.asistenciamedica.repositories

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tp3.asistenciamedica.entities.Turno
import com.tp3.asistenciamedica.entities.Usuario
import kotlinx.coroutines.tasks.await

class UsuarioRepository {

    private val db = Firebase.firestore


    suspend fun findUserById(id: String): Usuario? {

        val document = db.collection(Usuario.FIREBASE_COLLECTION).document(id)
            .get().await()

        val user = document.toObject(Usuario::class.java)

        user?.id = document.id

        return user
    }

    suspend fun findUserByCredentials(email: String, password: String): Usuario? {

        val documents = db.collection(Usuario.FIREBASE_COLLECTION)
            .whereEqualTo("email", email)
            .whereEqualTo("contrasena", password)
            .get()
            .await()

        if (documents.isEmpty){
            return null
        }

        val user = documents.toObjects(Usuario::class.java).first()
        user.id = documents.first().id

        return user
    }

    suspend fun userExists(email: String): Boolean {
        val document = db.collection(Usuario.FIREBASE_COLLECTION)
            .whereEqualTo("email", email)
            .get()
            .await()

        return !document.isEmpty
    }

    suspend fun create(usuario: Usuario) {
        val document = db.collection(Usuario.FIREBASE_COLLECTION).add(usuario).await()
        usuario.id = document.id
    }
}