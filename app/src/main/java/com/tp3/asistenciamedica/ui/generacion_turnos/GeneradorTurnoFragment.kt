package com.tp3.asistenciamedica.ui.generacion_turnos

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tp3.asistenciamedica.R
import com.tp3.asistenciamedica.daos.TurnoDao
import com.tp3.asistenciamedica.databinding.FragmentGeneradorTurnosBinding
import com.tp3.asistenciamedica.entities.Turno
import com.tp3.asistenciamedica.entities.TurnoEspecialidadEnum
import com.tp3.asistenciamedica.repositories.TurnoRepository
import com.tp3.asistenciamedica.session.Session
import kotlinx.coroutines.runBlocking
import java.lang.Exception
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.truncate


class GeneradorTurnoFragment : Fragment() {

    private val turnoRepo = TurnoRepository()

    companion object {
        fun newInstance() = GeneradorTurnoFragment()
    }



    private lateinit var viewModel: GeneradorTurnoViewModel
    private  var _binding: FragmentGeneradorTurnosBinding? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentGeneradorTurnosBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel = ViewModelProvider(this).get(GeneradorTurnoViewModel::class.java)

        binding.especialidad.options = TurnoEspecialidadEnum.values().toList().map { it.code }


        binding.submitTurnos.setOnClickListener {
            if(datosValidos()) {
                generateTurnos()
            }
        }


        return root
    }

    @SuppressLint("NewApi")
    private fun datosValidos(): Boolean {
        var success: Boolean = true;

        val date: Editable = binding.fechaAGenerar.text
        val regex = Regex("([01]?[0-9]|2[0-3]):[0-5][0-9]")
        val numRegex = Regex("-?\\d+(\\.\\d+)?")

        val horaFinal: Editable = binding.horaFinal.text
        val horaInicial: Editable = binding.horaInicial.text

        val especial: Any? = binding.especialidad.getSelectedItem()


        val duration: Editable = binding.duracion.text
        val separation: Editable = binding.separacion.text

        if (date.isNullOrEmpty()) {
            binding.fechaAGenerar.error = "La fecha a generar no puede estar vacia"
            success =  false
        }

        try {
            val parsedDate: LocalDate = LocalDate.parse(
                date,
                DateTimeFormatter.ofPattern("dd/MM/yyyy")
            )

            if (parsedDate.isBefore(LocalDate.now())) {
                binding.fechaAGenerar.error = "La fecha no puede ser anterior al dia de hoy."
                success = false
            }

        } catch (e: Exception) {
            binding.fechaAGenerar.error = "La fecha no tiene un formato valido: [dd/MM/YYYY]"
            success = false
        }





        if (especial == null || especial.toString() == "") {
            binding.especialidad.error = "La especialidad no puede estar vacia"
            success = false
        }

        if (!regex.matches(horaInicial.toString())) {
            binding.horaInicial.error = "El formato para la hora inicial es: HH:MM"
            success = false
        }
        if (!regex.matches(horaFinal.toString())) {
            binding.horaFinal.error = "El formato para la hora final es: HH:MM"
            success = false
        }





        if(!duration.matches(numRegex)) {
            binding.duracion.error = "La duración debe ser un numero"
            success = false
        }

        if (!separation.matches(numRegex)) {
            binding.separacion.error = "La separacion debe ser un numero"
            success = false
        }

        return success
    }


    @SuppressLint("NewApi")
    private fun generateTurnos() {
        // TODO: Replace logic with the correct API code


        var listDao = mutableListOf<TurnoDao>()

        val date: Editable = binding.fechaAGenerar.text
        val especial: String = binding.especialidad.getSelectedItem().toString()

        val duration: Editable = binding.duracion.text
        val horaFinal: Editable = binding.horaFinal.text
        val horaInicial: Editable = binding.horaInicial.text
        val separation: Editable = binding.separacion.text

        val currentUser = Session.current()

        val parsedDate: LocalDate = LocalDate.parse(
            date,
            DateTimeFormatter.ofPattern("dd/MM/yyyy")
        )


        val parsedHoraFinal: Double = Integer.valueOf(horaFinal.toString().replace(":", "")).toDouble()
        var parsedHoraInicial: Double = Integer.valueOf(horaInicial.toString().replace(":", "")).toDouble()

        var finalTime: ZonedDateTime = parsedDate.atStartOfDay(ZoneId.of("America/Argentina/Buenos_Aires"))
            .plusHours(
                truncate((parsedHoraFinal / 100)).toLong()
            )
            .plusMinutes(
                (parsedHoraFinal - truncate((parsedHoraFinal / 100)) * 100).toLong()
            )

        var dateTime: ZonedDateTime = parsedDate.atStartOfDay(ZoneId.of("America/Argentina/Buenos_Aires"))
            .plusHours(
                truncate((parsedHoraInicial / 100)).toLong()
            )
            .plusMinutes(
                (parsedHoraInicial - truncate((parsedHoraInicial / 100)) * 100).toLong()
            )


        while (dateTime <= finalTime ) {

            val turno = TurnoDao()

            turno.specialization = especial
            turno.dateTime = dateTime.toString()
            turno.doctorId = currentUser.id

            listDao.add(turno)

            val parsedDuration = Integer.valueOf(duration.toString())
            val parsedSeparation = Integer.valueOf(separation.toString())

            Log.d("TAG", "Turno datetime: "+ dateTime.toString())

            parsedHoraInicial += (parsedDuration + parsedSeparation)

            dateTime = dateTime.plusMinutes((parsedDuration + parsedSeparation).toLong())

        }


        generateTurnos(listDao)


    }


    private fun generateTurnos(turnosDao: List<TurnoDao>) {
        runBlocking {
            turnoRepo.saveTurnosDao(turnosDao)
            // TODO: Add something to show the user the process finished

        }
        findNavController().navigateUp()

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}