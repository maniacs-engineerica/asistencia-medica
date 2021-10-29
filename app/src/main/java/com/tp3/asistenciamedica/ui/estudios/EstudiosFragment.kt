package com.tp3.asistenciamedica.ui.estudios

import android.os.Bundle
import android.os.Handler
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.tp3.asistenciamedica.R
import com.tp3.asistenciamedica.adapters.EstudiosAdapter
import com.tp3.asistenciamedica.databinding.FragmentEstudiosBinding
import com.tp3.asistenciamedica.entities.Estudio
import com.tp3.asistenciamedica.entities.UsuarioTypeEnum
import com.tp3.asistenciamedica.repositories.EstudioRepository
import com.tp3.asistenciamedica.repositories.RecetaRepository
import com.tp3.asistenciamedica.session.Session
import kotlinx.coroutines.*

class EstudiosFragment : Fragment() {

    private lateinit var estudiosViewModel: EstudiosViewModel
    private var _binding: FragmentEstudiosBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var adapter: EstudiosAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        estudiosViewModel =
            ViewModelProvider(this).get(EstudiosViewModel::class.java)
        _binding = FragmentEstudiosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        setupRecycler()
    }

    override fun onStart() {
        super.onStart()

        val usuario = Session.current()

        val parentJob = Job()
        val scope = CoroutineScope(Dispatchers.Default + parentJob)

        scope.launch {
            val recetas = if (usuario.tipo == UsuarioTypeEnum.PACIENTE) {
                EstudioRepository().findEstudiosByPacientId(usuario.id)
            } else {
                EstudioRepository().findEstudiosByProfesionalId(usuario.id)
            }
            withContext(Dispatchers.Main) {
                estudiosViewModel.setEstudios(recetas)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.estudios_fragment, menu)
        val add = menu.findItem(R.id.add)
        add.isVisible = Session.current().tipo == UsuarioTypeEnum.MEDICO
        add.setOnMenuItemClickListener {
            findNavController().navigate(EstudiosFragmentDirections.actionEstudiosToEstudio(null))
            true
        }
    }

    private fun setupRecycler() {
        adapter = EstudiosAdapter()
        adapter.onEstudioClick = {
            findNavController().navigate(EstudiosFragmentDirections.actionEstudiosToEstudio(it.idEstudio))
        }
        binding.estudios.adapter = adapter

        estudiosViewModel.estudios.observe(viewLifecycleOwner, { result ->
            adapter.swapEstudios(result)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}