package com.tp3.asistenciamedica.ui.doctor

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.tp3.asistenciamedica.databinding.FragmentTurnoidBinding
import com.tp3.asistenciamedica.repositories.TurnoRepository
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.util.*

class TurnoPacienteFragment : Fragment() {

    private lateinit var turnoPacienteviewModel: TurnoPacienteViewModel
    private var _binding: FragmentTurnoidBinding? = null

    private val binding get() = _binding!!

    private val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        turnoPacienteviewModel =
            ViewModelProvider(this).get(TurnoPacienteViewModel::class.java)

        _binding = FragmentTurnoidBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NewApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        turnoPacienteviewModel.turno.observe(viewLifecycleOwner, { result ->
            binding.txtNomPaciente.setText(result.paciente!!.nombreCompleto)
            binding.txtNomDoctor.setText(result.doctor.nombreCompleto)
            binding.txtNomEspecialidad.setText(result.specialization)
            val date = Date.from(ZonedDateTime.parse(result.dateTime).toInstant())
            binding.txtNomFecha.setText(formatter.format(date))
        })

        binding.anadirInfo.setOnClickListener { agregarInformacion() }
        binding.btnverpaciente.setOnClickListener { verInformacionPaciente() }


    }

    override fun onStart() {
        super.onStart()

        val id = TurnoPacienteFragmentArgs.fromBundle(requireArguments()).turnoId ?: return

        val parentJob = Job()
        val scope = CoroutineScope(Dispatchers.Default + parentJob)

        scope.launch {
            val turno = TurnoRepository().findTurnoById(id) ?: return@launch
            withContext(Dispatchers.Main){
                turnoPacienteviewModel.setTurno(turno)
            }
        }
    }

    private fun agregarInformacion() {
        lifecycleScope.launch {
            findNavController().navigate(TurnoPacienteFragmentDirections.actionNavigationDoctorTurnoPacienteToTurnoInformacionFragment("4MOd85EaF1KXvVG0yAGn"))
        }
    }

    private fun verInformacionPaciente() {
        lifecycleScope.launch {
            findNavController().navigate(TurnoPacienteFragmentDirections.actionNavigationDoctorTurnoPacienteToTurnoVerInformacionFragment("4MOd85EaF1KXvVG0yAGn"))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}