package com.tp3.asistenciamedica.ui

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.tp3.asistenciamedica.R
import com.tp3.asistenciamedica.databinding.ActivityDoctorMainBinding

class MainActivityDoctor : AppCompatActivity() {

    private lateinit var binding: ActivityDoctorMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDoctorMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navDoctorView

        val navController = findNavController(R.id.nav_host_fragment_activity_doctor_main)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_doctor_inicio, R.id.navigation_doctor_recetas,
                R.id.navigation_doctor_estudios, R.id.navigation_doctor_turnos
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}