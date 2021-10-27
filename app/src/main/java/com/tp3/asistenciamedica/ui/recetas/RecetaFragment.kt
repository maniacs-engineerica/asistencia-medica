package com.tp3.asistenciamedica.ui.recetas

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tp3.asistenciamedica.R
import com.tp3.asistenciamedica.databinding.FragmentRecetasBinding
import com.tp3.asistenciamedica.databinding.RecetaFragmentBinding

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

    override fun onStart() {
        super.onStart()

        val recetaId = RecetaFragmentArgs.fromBundle(requireArguments()).recetaId

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}