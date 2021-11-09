package com.tp3.asistenciamedica.repositories

import android.net.Uri
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await
import java.io.File
import java.io.FileInputStream

class StorageRepository {

    companion object {
        val FOLDER: String = "gs://asistencia-medica-44691.appspot.com"
    }

    private val storage = Firebase.storage(FOLDER)

    suspend fun upload(uri: Uri, name: String){
        val storageRef = storage.getReference(name)
        storageRef.putFile(uri).await()
    }

}