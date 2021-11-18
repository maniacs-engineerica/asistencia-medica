package com.tp3.asistenciamedica.ui.doctor

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.tp3.asistenciamedica.R
import com.tp3.asistenciamedica.databinding.FragmentInfoturnopacienteBinding
import com.tp3.asistenciamedica.databinding.FragmentTurnoidBinding
import com.tp3.asistenciamedica.repositories.TurnoRepository
import kotlinx.coroutines.*
import java.time.ZonedDateTime
import java.util.*

class TurnoVerInformacionFragment : Fragment() {

    private lateinit var turnoVerInformacionViewModel: TurnoVerInformacionViewModel
    private lateinit var id: String
    private var _binding: FragmentInfoturnopacienteBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        turnoVerInformacionViewModel =
            ViewModelProvider(this).get(TurnoVerInformacionViewModel::class.java)

        _binding = FragmentInfoturnopacienteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        id = TurnoVerInformacionFragmentArgs.fromBundle(requireArguments()).turnoId ?: return

        val parentJob = Job()
        val scope = CoroutineScope(Dispatchers.Default + parentJob)

        scope.launch {
            val turno = TurnoRepository().findTurnoById(id) ?: return@launch
            withContext(Dispatchers.Main){
                turnoVerInformacionViewModel.setTurno(turno)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home ->
                findNavController().navigate(TurnoVerInformacionFragmentDirections.actionNavigationDoctorVerInfoPacienteToNavigationDoctorTurnoPaciente(id))
        }
        return true
    }

    @SuppressLint("NewApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)

        turnoVerInformacionViewModel.turno.observe(viewLifecycleOwner, { result ->
            binding.txtNomPacienteInfo.setText(result.paciente!!.nombreCompleto)
            binding.txtDNIPac.setText(result.paciente!!.dni)
        })

        binding.btnVerEstudios.setOnClickListener { verEstudios() }

    }

    private fun verEstudios() {
        lifecycleScope.launch {
            findNavController().navigate(TurnoVerInformacionFragmentDirections.actionNavigationDoctorVerInfoPacienteToPacienteEstudiosFragment(id))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}