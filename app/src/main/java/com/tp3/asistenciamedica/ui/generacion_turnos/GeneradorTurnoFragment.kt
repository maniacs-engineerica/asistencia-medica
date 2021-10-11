package com.tp3.asistenciamedica.ui.generacion_turnos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.tp3.asistenciamedica.databinding.FragmentGeneradorTurnosBinding


class GeneradorTurnoFragment : Fragment() {

    companion object {
        fun newInstance() = GeneradorTurnoFragment()
    }

    private lateinit var viewModel: GeneradorTurnoViewModel
    private  var _binding: FragmentGeneradorTurnosBinding? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentGeneradorTurnosBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel = ViewModelProvider(this).get(GeneradorTurnoViewModel::class.java)


        return root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}