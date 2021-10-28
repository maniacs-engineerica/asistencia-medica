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
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tp3.asistenciamedica.databinding.FragmentGeneradorTurnosBinding
import com.tp3.asistenciamedica.entities.Turno
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.truncate


class GeneradorTurnoFragment : Fragment() {

    private val db = Firebase.firestore


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

        binding.submitTurnos.setOnClickListener {
            generateTurnos()
        }


        return root
    }


    @SuppressLint("NewApi")
    private fun generateTurnos() {
        // TODO: Replace logic with the correct API code

        // Assumes that here we are with valid data
        val date: Editable = binding.fechaAGenerar.text
        val especial: Editable = binding.especialidad.text

        val duration: Editable = binding.duracion.text
        val horaFinal: Editable = binding.horaFinal.text
        val horaInicial: Editable = binding.horaInicial.text
        val separation: Editable = binding.separacion.text

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

        while (dateTime <= finalTime ) {

            val turno: Turno = Turno()
                .withSpecialization(especial.toString())
                //.withDoctorId("id")

                .withDate(dateTime.toString())


            generateTurno(turno)

            val parsedDuration = Integer.valueOf(duration.toString())
            val parsedSeparation = Integer.valueOf(separation.toString())


            parsedHoraInicial += (parsedDuration + parsedSeparation)

            dateTime = parsedDate.atStartOfDay(ZoneId.of("America/Argentina/Buenos_Aires"))
                .plusHours(
                    truncate((parsedHoraInicial / 100)).toLong()
                )
                .plusMinutes(
                    (parsedHoraInicial - truncate((parsedHoraInicial / 100)) * 100).toLong()
                )


        }



    }


    private fun generateTurno(turno: Turno) {
        db.collection(Turno.FIREBASE_COLLECTION)
            .add(turno)
            .addOnSuccessListener { documentReference ->
                Log.d("TAG", "Document generated: "+ documentReference)
            }
            .addOnFailureListener { e ->
                Log.d("TAG", "Error trying to insert turno: "+ e)
            }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}