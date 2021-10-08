package com.tp3.asistenciamedica.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tp3.asistenciamedica.R
import com.tp3.asistenciamedica.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

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
        //TODO: chequear datos de login
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun datosValidos(): Boolean {
        //TODO: validar
        return true
    }
}