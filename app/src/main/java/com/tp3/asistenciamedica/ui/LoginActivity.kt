package com.tp3.asistenciamedica.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tp3.asistenciamedica.R
import com.tp3.asistenciamedica.databinding.ActivityLoginBinding
import com.tp3.asistenciamedica.entities.UsuarioTypeEnum
import com.tp3.asistenciamedica.repositories.UsuarioRepository
import com.tp3.asistenciamedica.session.Session
import com.tp3.asistenciamedica.utils.KeyboardUtils
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.login.setOnClickListener {
            ingresar()
        }

        binding.registrarme.setOnClickListener {
            abrirRegistracion()
        }
    }

    private fun abrirRegistracion() {
        val intent = Intent(this, RegistracionActivity::class.java)
        startActivity(intent)
    }

    private fun ingresar() {
        if (!datosValidos()) {
            return
        }

        binding.login.isEnabled = false
        binding.login.setText(R.string.ingresando)

        KeyboardUtils.close(this)

        val repository = UsuarioRepository()

        val user = runBlocking {
            repository.findUserByCredentials(
                binding.username.text.toString(),
                binding.password.text.toString()
            )
        }

        if (user == null) {
            binding.login.isEnabled = true
            binding.login.setText(R.string.ingresar)
            Snackbar.make(binding.login, R.string.usuario_no_existente, 3000).show()
            return
        }

        //TODO: esto hay que borrarlo cuando el id ya este
        if (user.id != null){
            Session.login(user)
        }

        if (user.tipo == UsuarioTypeEnum.PACIENTE) {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        } else {
            val intent = Intent(this, MainActivityDoctor::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    private fun datosValidos(): Boolean {

        if (binding.username.text.isNullOrEmpty()) {
            binding.username.error = getString(R.string.username_invalido)
            return false
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(binding.username.text).matches()) {
            binding.username.error = getString(R.string.username_invalido)
            return false
        }

        if (binding.password.text.isNullOrEmpty()) {
            binding.password.error = getString(R.string.password_invalido)
            return false
        }

        return true
    }
}