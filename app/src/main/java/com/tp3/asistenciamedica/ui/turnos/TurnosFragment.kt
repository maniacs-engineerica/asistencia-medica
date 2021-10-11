package com.tp3.asistenciamedica.ui.turnos

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.tp3.asistenciamedica.databinding.FragmentTurnosBinding
import com.tp3.asistenciamedica.ui.estudios.EstudiosFragmentDirections
import com.tp3.asistenciamedica.ui.recetas.TurnosViewModel

class TurnosFragment : Fragment() {

    private lateinit var turnosViewModel: TurnosViewModel
    private var _binding: FragmentTurnosBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        turnosViewModel =
            ViewModelProvider(this).get(TurnosViewModel::class.java)

        _binding = FragmentTurnosBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(TurnosFragmentDirections.actionTurnosToTurno())
        }, 2000)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}