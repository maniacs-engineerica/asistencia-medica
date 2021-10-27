package com.tp3.asistenciamedica.ui.estudios

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.tp3.asistenciamedica.R
import com.tp3.asistenciamedica.databinding.EstudioFragmentBinding
import com.tp3.asistenciamedica.databinding.RecetaFragmentBinding
import com.tp3.asistenciamedica.ui.recetas.RecetaViewModel

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

    override fun onStart() {
        super.onStart()

        val estudioId = EstudioFragmentArgs.fromBundle(requireArguments()).estudioId

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}