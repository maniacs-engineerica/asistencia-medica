package com.tp3.asistenciamedica.ui.doctor

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.tp3.asistenciamedica.R
import com.tp3.asistenciamedica.adapters.EstudiosAdapter
import com.tp3.asistenciamedica.databinding.FragmentEstudiosDoctorBinding
import com.tp3.asistenciamedica.repositories.EstudioRepository
import com.tp3.asistenciamedica.repositories.TurnoRepository
import com.tp3.asistenciamedica.ui.estudios.EstudiosFragmentDirections
import kotlinx.coroutines.*

class PacienteEstudiosFragment : Fragment() {

    private lateinit var viewModel: PacienteEstudiosViewModel
    private var _binding: FragmentEstudiosDoctorBinding? = null

    private val binding get() = _binding!!
    private lateinit var adapter: EstudiosAdapter

    private lateinit var idTurno: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProvider(this).get(PacienteEstudiosViewModel::class.java)
            _binding = FragmentEstudiosDoctorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecycler()
    }

    override fun onStart() {
        super.onStart()

        idTurno = TurnoInformacionFragmentArgs.fromBundle(requireArguments()).turnoId ?: return

        val parentJob = Job()
        val scope = CoroutineScope(Dispatchers.Default + parentJob)

        binding.estudiosPaciente.showShimmerAdapter()

        scope.launch {
            val turno = TurnoRepository().findTurnoById(idTurno) ?: return@launch
            val idPaciente = turno.paciente?.id

            val estudios = EstudioRepository().findEstudiosByPacientId(idPaciente)
            withContext(Dispatchers.Main) {
                viewModel.setEstudios(estudios)
                binding.estudiosPaciente.hideShimmerAdapter()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.estudios_fragment, menu)
    }

    private fun setupRecycler() {
        adapter = EstudiosAdapter()
        adapter.onEstudioClick = {
            //findNavController().navigate(EstudiosFragmentDirections.actionEstudiosToEstudio(it.idEstudio))
        }
        binding.estudiosPaciente.adapter = adapter

        viewModel.estudios.observe(viewLifecycleOwner, { result ->
            adapter.swapEstudios(result)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}