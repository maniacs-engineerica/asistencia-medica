package com.tp3.asistenciamedica.ui.doctor

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.tp3.asistenciamedica.R
import com.tp3.asistenciamedica.databinding.ActivityMainBinding

class MainActivityDoctor : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_doctor_main)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_doctor_inicio/*, R.id.navigation_recetas, R.id.navigation_historial,
                R.id.navigation_estudios, R.id.navigation_turnos*/
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}