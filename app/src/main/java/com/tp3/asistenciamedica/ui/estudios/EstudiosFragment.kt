package com.tp3.asistenciamedica.ui.estudios

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.tp3.asistenciamedica.databinding.FragmentEstudiosBinding

class EstudiosFragment : Fragment() {

    private lateinit var estudiosViewModel: EstudiosViewModel
    private var _binding: FragmentEstudiosBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        estudiosViewModel =
            ViewModelProvider(this).get(EstudiosViewModel::class.java)
        _binding = FragmentEstudiosBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Handler(Looper.getMainLooper()).postDelayed({
            EstudiosFragmentDirections.actionEstudiosToEstudio()
        }, 2000)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}