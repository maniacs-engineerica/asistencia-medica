package com.tp3.asistenciamedica.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tp3.asistenciamedica.R
import com.tp3.asistenciamedica.databinding.ActivityLoginBinding
import com.tp3.asistenciamedica.databinding.ActivityRegistracionBinding

class RegistracionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistracionBinding

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
    }

    private fun datosValidos(): Boolean {
        /*
        if (binding.email.text.isNullOrEmpty()){
            binding.email.error = getString(R.string.username_invalido)
            return false
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(binding.email.text).matches()){
            binding.email.error = getString(R.string.username_invalido)
            return false
        }

        if (binding.password.text.isNullOrEmpty()){
            binding.password.error = getString(R.string.password_invalido)
            return false
        }

        if (binding.password.text.toString() != binding.passwordRepeat.text.toString()){
            binding.passwordRepeat.error = getString(R.string.password_no_coincide)
            return false
        }

        if (binding.firstName.text.isNullOrEmpty()){
            binding.firstName.error = getString(R.string.nombre_invalido)
            return false
        }

        if (binding.lastName.text.isNullOrEmpty()){
            binding.lastName.error = getString(R.string.apellido_invalido)
            return false
        }

        if (binding.dni.text.isNullOrEmpty()){
            binding.dni.error = getString(R.string.dni_invalido)
            return false
        }

        if (binding.phoneNumber.text.isNullOrEmpty()){
            binding.phoneNumber.error = getString(R.string.telefono_invalido)
            return false
        }
        */
        return true
    }
}