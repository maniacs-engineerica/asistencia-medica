package com.tp3.asistenciamedica.ui.recetas

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.tp3.asistenciamedica.R
import com.tp3.asistenciamedica.adapters.RecetasAdapter
import com.tp3.asistenciamedica.databinding.FragmentRecetasBinding
import com.tp3.asistenciamedica.entities.UsuarioTypeEnum
import com.tp3.asistenciamedica.repositories.RecetaRepository
import com.tp3.asistenciamedica.session.Session
import com.tp3.asistenciamedica.ui.estudios.EstudiosFragmentDirections
import kotlinx.coroutines.*

class RecetasFragment : Fragment() {

    private lateinit var recetasViewModel: RecetasViewModel
    private var _binding: FragmentRecetasBinding? = null

    private val binding get() = _binding!!

    private lateinit var adapter: RecetasAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        recetasViewModel =
            ViewModelProvider(this).get(RecetasViewModel::class.java)

        _binding = FragmentRecetasBinding.inflate(inflater, container, false)
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

        binding.recetas.showShimmerAdapter()

        scope.launch {
            val recetas = if (usuario.tipo == UsuarioTypeEnum.PACIENTE) {
                RecetaRepository().findRecetaByPacientId(usuario.id)
            } else {
                RecetaRepository().findRecetaByProfesionalId(usuario.id)
            }
            withContext(Dispatchers.Main) {
                if (!isAdded) return@withContext
                recetasViewModel.setRecetas(recetas)
                binding.recetas.hideShimmerAdapter()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.recetas_fragment, menu)
        val add = menu.findItem(R.id.add)
        add.isVisible = Session.current().tipo == UsuarioTypeEnum.MEDICO
        add.setOnMenuItemClickListener {
            findNavController().navigate(RecetasFragmentDirections.actionRecetasToReceta(null))
            true
        }
    }

    private fun setupRecycler() {
        adapter = RecetasAdapter()
        adapter.onRecetaClick = {
            findNavController().navigate(RecetasFragmentDirections.actionRecetasToReceta(it.idReceta))
        }
        binding.recetas.adapter = adapter

        recetasViewModel.recetas.observe(viewLifecycleOwner, { result ->
            adapter.swapRecetas(result)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}