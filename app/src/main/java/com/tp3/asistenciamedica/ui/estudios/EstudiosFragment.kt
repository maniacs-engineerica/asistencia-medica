package com.tp3.asistenciamedica.ui.estudios

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.tp3.asistenciamedica.EstudiosAdapter
import com.tp3.asistenciamedica.databinding.FragmentEstudiosBinding

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
        setupRecycler()
    }

    private fun setupRecycler(){
        adapter = EstudiosAdapter()
        adapter.onEstudioClick = {
            findNavController().navigate(EstudiosFragmentDirections.actionEstudiosToEstudio())
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