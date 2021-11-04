package com.tp3.asistenciamedica.ui.turnos

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.tp3.asistenciamedica.databinding.TurnoFragmentBinding
import com.tp3.asistenciamedica.repositories.TurnoRepository
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.util.*

class TurnoFragment : Fragment() {

    private var _binding: TurnoFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: TurnoViewModel

    private val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProvider(this).get(TurnoViewModel::class.java)

        _binding = TurnoFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NewApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.turno.observe(viewLifecycleOwner, { result ->
            binding.paciente.setText(result.paciente!!.nombreCompleto)
            binding.profesional.setText(result.doctor.nombreCompleto)
            binding.especialidad.setText(result.specialization)

            val date = Date.from(ZonedDateTime.parse(result.dateTime).toInstant())
            binding.fecha.setText(formatter.format(date))
        })
    }

    override fun onStart() {
        super.onStart()

        val turnoId = TurnoFragmentArgs.fromBundle(requireArguments()).turnoId

        val parentJob = Job()
        val scope = CoroutineScope(Dispatchers.Default + parentJob)

        scope.launch {
            val turno = TurnoRepository().findTurnoById(turnoId) ?: return@launch
            withContext(Dispatchers.Main){
                viewModel.setTurno(turno)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}