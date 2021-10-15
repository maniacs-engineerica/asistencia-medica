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

class RecetasFragment : Fragment() {

    private lateinit var recetasViewModel: RecetasViewModel
    private var _binding: FragmentRecetasBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var adapter: RecetasAdapter

    val db = Firebase.firestore

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

        db.collection("recetas").get().addOnSuccessListener {
            recetasViewModel.setRecetas(it.toObjects(Receta::class.java))
        }
    }

    private fun setupRecycler() {
        adapter = RecetasAdapter()
        adapter.onRecetaClick = {
            findNavController().navigate(RecetasFragmentDirections.actionRecetasToReceta())
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