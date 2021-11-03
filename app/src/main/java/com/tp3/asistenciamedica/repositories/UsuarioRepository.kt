package com.tp3.asistenciamedica.repositories

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tp3.asistenciamedica.entities.Turno
import com.tp3.asistenciamedica.entities.Usuario
import com.tp3.asistenciamedica.entities.UsuarioTypeEnum
import kotlinx.coroutines.tasks.await

class UsuarioRepository {

    private val db = Firebase.firestore


    suspend fun findUserById(id: String): Usuario? {

        val document = db.collection(Usuario.FIREBASE_COLLECTION).document(id)
            .get().await()

        return document.toUser()
    }

    suspend fun findUserByCredentials(email: String, password: String): Usuario? {

        val documents = db.collection(Usuario.FIREBASE_COLLECTION)
            .whereEqualTo("email", email)
            .whereEqualTo("contrasena", password)
            .get()
            .await()

        if (documents.isEmpty) {
            return null
        }

        return documents.toUsers().first()
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

    suspend fun allPacientes(nombre: String? = null): List<Usuario> {
        val documents = db.collection(Usuario.FIREBASE_COLLECTION)
            .whereEqualTo("tipo", UsuarioTypeEnum.PACIENTE.name)
            .get()
            .await()

        val users = documents.toUsers()

        return nombre?.let {
            users.filter { it.nombre.contains(nombre, true) || it.apellido.contains(nombre) }
        } ?: users
    }
}

private fun QuerySnapshot.toUsers(): List<Usuario> {
    return mapNotNull { it.toUser() }
}

private fun DocumentSnapshot.toUser(): Usuario? {
    val user = toObject(Usuario::class.java)
    user?.id = id
    return user
}
