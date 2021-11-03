package com.tp3.asistenciamedica.ui.turnos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import android.widget.Button
import androidx.navigation.findNavController
import com.tp3.asistenciamedica.R
import com.tp3.asistenciamedica.databinding.EstudioFragmentBinding
import com.tp3.asistenciamedica.databinding.TurnoFragmentBinding
import com.tp3.asistenciamedica.ui.estudios.EstudioViewModel

class TurnoFragment : Fragment() {

    private var _binding: TurnoFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: TurnoViewModel

    lateinit var v:View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v= inflater.inflate(R.layout.turno_fragment, container, false)

        return v
        viewModel =
            ViewModelProvider(this).get(TurnoViewModel::class.java)

        _binding = TurnoFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        val turnoId = TurnoFragmentArgs.fromBundle(requireArguments()).turnoId
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
