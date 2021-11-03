package com.tp3.asistenciamedica.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tp3.asistenciamedica.R
import com.tp3.asistenciamedica.databinding.ActivityRegistracionBinding
import com.tp3.asistenciamedica.entities.Usuario
import com.tp3.asistenciamedica.entities.UsuarioTypeEnum
import com.tp3.asistenciamedica.repositories.UsuarioRepository
import com.tp3.asistenciamedica.session.Session
import com.tp3.asistenciamedica.utils.KeyboardUtils
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class RegistracionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistracionBinding

    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistracionBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.registrarse.setOnClickListener { registrar() }
    }

    private fun registrar() {
        if (!datosValidos()) {
            return
        }

        binding.registrarse.isEnabled = false
        binding.registrarse.text = getString(R.string.guardando)

        KeyboardUtils.close(this)

        val repository = UsuarioRepository()

        lifecycleScope.launch {

            val exists = repository.userExists(binding.email.text.toString())

            if (exists) {
                binding.registrarse.isEnabled = true
                binding.registrarse.text = getString(R.string.registrarme)
                Snackbar.make(binding.email, R.string.usuario_existente, 3000).show()
                return@launch
            }

            val usuario = Usuario(
                binding.firstName.text.toString(),
                binding.lastName.text.toString(),
                binding.email.text.toString(),
                binding.password.text.toString(),
                binding.dni.text.toString(),
                binding.phoneNumber.text.toString(),
                UsuarioTypeEnum.PACIENTE
            )

            repository.create(usuario)
            finish()
        }
    }

    private fun datosValidos(): Boolean {

        if (binding.email.text.isNullOrEmpty()) {
            binding.email.error = getString(R.string.username_invalido)
            return false
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(binding.email.text).matches()) {
            binding.email.error = getString(R.string.username_invalido)
            return false
        }

        if (binding.password.text.isNullOrEmpty()) {
            binding.password.error = getString(R.string.password_invalido)
            return false
        }

        if (binding.password.text.toString() != binding.passwordRepeat.text.toString()) {
            binding.passwordRepeat.error = getString(R.string.password_no_coincide)
            return false
        }

        if (binding.firstName.text.isNullOrEmpty()) {
            binding.firstName.error = getString(R.string.nombre_invalido)
            return false
        }

        if (binding.lastName.text.isNullOrEmpty()) {
            binding.lastName.error = getString(R.string.apellido_invalido)
            return false
        }

        if (binding.dni.text.isNullOrEmpty()) {
            binding.dni.error = getString(R.string.dni_invalido)
            return false
        }

        if (binding.phoneNumber.text.isNullOrEmpty()) {
            binding.phoneNumber.error = getString(R.string.telefono_invalido)
            return false
        }

        return true
    }
}