package com.tp3.asistenciamedica.ui.recetas

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.tp3.asistenciamedica.R
import com.tp3.asistenciamedica.daos.RecetaDao
import com.tp3.asistenciamedica.databinding.FragmentRecetasBinding
import com.tp3.asistenciamedica.databinding.RecetaFragmentBinding
import com.tp3.asistenciamedica.entities.Receta
import com.tp3.asistenciamedica.entities.Usuario
import com.tp3.asistenciamedica.entities.UsuarioTypeEnum
import com.tp3.asistenciamedica.repositories.RecetaRepository
import com.tp3.asistenciamedica.repositories.UsuarioRepository
import com.tp3.asistenciamedica.session.Session
import com.tp3.asistenciamedica.utils.DateUtils
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

class RecetaFragment : Fragment() {

    private var _binding: RecetaFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: RecetaViewModel

    private val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProvider(this).get(RecetaViewModel::class.java)

        _binding = RecetaFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val usuario = Session.current()

        val exists = RecetaFragmentArgs.fromBundle(requireArguments()).recetaId != null

        if (usuario.tipo == UsuarioTypeEnum.PACIENTE || exists) {
            binding.paciente.isEnabled = false
            binding.fecha.isEnabled = false
            binding.descripcion.isEnabled = false
            binding.emitir.visibility = GONE
        } else {
            binding.profesional.setSelectedItem(usuario)
            lifecycleScope.launch {
                binding.paciente.options = UsuarioRepository().allPacientes()
            }
        }

        binding.emitir.setOnClickListener { emitirReceta() }

        viewModel.receta.observe(viewLifecycleOwner, { result ->
            binding.paciente.setSelectedItem(result.paciente)
            binding.profesional.setSelectedItem(result.doctor)
            binding.descripcion.setText(result.descripcion)
            binding.fecha.setText(formatter.format(result.fecha))
        })
    }

    override fun onStart() {
        super.onStart()

        val id = RecetaFragmentArgs.fromBundle(requireArguments()).recetaId ?: return

        val parentJob = Job()
        val scope = CoroutineScope(Dispatchers.Default + parentJob)

        scope.launch {
            val receta = RecetaRepository().findRecetaById(id) ?: return@launch
            withContext(Dispatchers.Main){
                viewModel.setReceta(receta)
            }
        }
    }

    private fun emitirReceta() {
        if (binding.paciente.getSelectedItem() == null){
            binding.paciente.error = getString(R.string.validacion_paciente)
            return
        }

        if (!DateUtils.isValidDate(binding.fecha.text.toString(), "dd/MM/yyyy")){
            binding.fecha.error = getString(R.string.validacion_fecha)
            return
        }

        if (binding.descripcion.text.isEmpty()){
            binding.descripcion.error = getString(R.string.validacion_receta)
            return
        }

        val receta = RecetaDao()
        receta.pacienteId = (binding.paciente.getSelectedItem() as Usuario).id
        receta.doctorId = (binding.profesional.getSelectedItem() as Usuario).id
        receta.fecha = formatter.parse(binding.fecha.text.toString())!!
        receta.descripcion = binding.descripcion.text.toString()

        lifecycleScope.launch {
            RecetaRepository().create(receta)
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}