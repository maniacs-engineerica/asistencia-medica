package com.tp3.asistenciamedica.ui.estudios

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.tp3.asistenciamedica.R
import com.tp3.asistenciamedica.daos.EstudioDao
import com.tp3.asistenciamedica.databinding.EstudioFragmentBinding
import com.tp3.asistenciamedica.entities.Usuario
import com.tp3.asistenciamedica.entities.UsuarioTypeEnum
import com.tp3.asistenciamedica.repositories.EstudioRepository
import com.tp3.asistenciamedica.repositories.RecetaRepository
import com.tp3.asistenciamedica.repositories.UsuarioRepository
import com.tp3.asistenciamedica.session.Session
import com.tp3.asistenciamedica.utils.DateUtils
import kotlinx.coroutines.*

class EstudioFragment : Fragment() {

    private var _binding: EstudioFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: EstudioViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProvider(this).get(EstudioViewModel::class.java)

        _binding = EstudioFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val usuario = Session.current()

        val exists = EstudioFragmentArgs.fromBundle(requireArguments()).estudioId != null

        if (usuario.tipo == UsuarioTypeEnum.PACIENTE || exists) {
            binding.paciente.isEnabled = false
            binding.fecha.isEnabled = false
            binding.estudio.isEnabled = false
            binding.resultado.isEnabled = false
            binding.subir.visibility = View.GONE
        } else {
            binding.profesional.setSelectedItem(usuario)
            lifecycleScope.launch {
                binding.paciente.options = UsuarioRepository().allPacientes()
            }
        }

        binding.subir.setOnClickListener { subir() }

        viewModel.estudio.observe(viewLifecycleOwner, { result ->
            binding.paciente.setSelectedItem(result.paciente)
            binding.profesional.setSelectedItem(result.doctor)
            binding.estudio.setText(result.nombre)
            binding.resultado.setText(result.resultado)
            binding.fecha.setText(result.fecha)
        })
    }

    override fun onStart() {
        super.onStart()

        val id = EstudioFragmentArgs.fromBundle(requireArguments()).estudioId ?: return

        val parentJob = Job()
        val scope = CoroutineScope(Dispatchers.Default + parentJob)

        scope.launch {
            val estudio = EstudioRepository().findEstudioById(id) ?: return@launch
            withContext(Dispatchers.Main){
                viewModel.setEstudio(estudio)
            }
        }
    }

    private fun subir() {
        if (binding.paciente.getSelectedItem() == null){
            binding.paciente.error = getString(R.string.validacion_paciente)
            return
        }

        if (binding.estudio.text.isEmpty()){
            binding.estudio.error = getString(R.string.validacion_estudio)
            return
        }

        if (!DateUtils.isValidDate(binding.fecha.text.toString(), "dd/MM/yyyy")){
            binding.fecha.error = getString(R.string.validacion_fecha)
            return
        }

        if (binding.resultado.text.isEmpty()){
            binding.resultado.error = getString(R.string.validacion_resultado)
            return
        }

        val estudio = EstudioDao()
        estudio.pacienteId = (binding.paciente.getSelectedItem() as Usuario).id
        estudio.doctorId = (binding.profesional.getSelectedItem() as Usuario).id
        estudio.nombre = binding.estudio.text.toString()
        estudio.fecha = binding.fecha.text.toString()
        estudio.resultado = binding.resultado.text.toString()

        lifecycleScope.launch {
            EstudioRepository().create(estudio)
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}