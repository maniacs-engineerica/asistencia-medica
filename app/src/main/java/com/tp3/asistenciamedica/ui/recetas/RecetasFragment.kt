package com.tp3.asistenciamedica.ui.recetas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tp3.asistenciamedica.adapters.RecetasAdapter
import com.tp3.asistenciamedica.databinding.FragmentRecetasBinding
import com.tp3.asistenciamedica.entities.Receta
import com.tp3.asistenciamedica.entities.UsuarioTypeEnum
import com.tp3.asistenciamedica.repositories.EstudioRepository
import com.tp3.asistenciamedica.repositories.RecetaRepository
import com.tp3.asistenciamedica.session.Session
import kotlinx.coroutines.runBlocking

class RecetasFragment : Fragment() {

    private lateinit var recetasViewModel: RecetasViewModel
    private var _binding: FragmentRecetasBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
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
        setupRecycler()
    }

    override fun onStart() {
        super.onStart()

        val usuario = Session.current()

        val recetas = if (usuario.tipo == UsuarioTypeEnum.PACIENTE){
            runBlocking {
                RecetaRepository().findRecetaByPacientId(usuario.id)
            }
        } else {
            runBlocking {
                RecetaRepository().findRecetaByProfesionalId(usuario.id)
            }
        }
        recetasViewModel.setRecetas(recetas)
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