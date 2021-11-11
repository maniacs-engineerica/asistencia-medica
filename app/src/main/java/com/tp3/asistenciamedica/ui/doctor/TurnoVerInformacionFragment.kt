package com.tp3.asistenciamedica.ui.doctor

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tp3.asistenciamedica.R
import com.tp3.asistenciamedica.databinding.FragmentInfoturnopacienteBinding
import com.tp3.asistenciamedica.databinding.FragmentTurnoidBinding

class TurnoVerInformacionFragment : Fragment() {

    private lateinit var turnoVerInformacionViewModel: TurnoVerInformacionViewModel
    private var _binding: FragmentInfoturnopacienteBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        turnoVerInformacionViewModel =
            ViewModelProvider(this).get(TurnoVerInformacionViewModel::class.java)

        _binding = FragmentInfoturnopacienteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}