package com.tp3.asistenciamedica.ui.recetas

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.tp3.asistenciamedica.R
import com.tp3.asistenciamedica.databinding.FragmentRecetasBinding
import com.tp3.asistenciamedica.databinding.RecetaFragmentBinding
import com.tp3.asistenciamedica.entities.UsuarioTypeEnum
import com.tp3.asistenciamedica.repositories.RecetaRepository
import com.tp3.asistenciamedica.repositories.UsuarioRepository
import com.tp3.asistenciamedica.session.Session
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class RecetaFragment : Fragment() {

    private var _binding: RecetaFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: RecetaViewModel

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

        if (usuario.tipo == UsuarioTypeEnum.PACIENTE) {
            binding.paciente.isEnabled = false
            binding.fecha.isEnabled = false
            binding.descripcion.isEnabled = false
            binding.emitir.visibility = GONE
        } else {
            binding.profesional.setText(usuario.nombreCompleto)
        }

        binding.emitir.setOnClickListener { emitirReceta() }

        viewModel.receta.observe(viewLifecycleOwner, { result ->
            binding.paciente.setText(result.paciente.nombreCompleto)
            binding.profesional.setText(result.doctor.nombreCompleto)
            binding.descripcion.setText(result.descripcion)
            binding.fecha.setText(result.fecha)
        })
    }

    override fun onStart() {
        super.onStart()

        val id = RecetaFragmentArgs.fromBundle(requireArguments()).recetaId

        lifecycleScope.launch {
            val receta = RecetaRepository().findRecetaById(id) ?: return@launch
            viewModel.setReceta(receta)
        }
    }

    private fun emitirReceta(){

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}