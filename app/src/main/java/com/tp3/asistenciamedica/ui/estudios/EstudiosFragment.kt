package com.tp3.asistenciamedica.ui.estudios

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.tp3.asistenciamedica.databinding.FragmentEstudiosBinding
import com.tp3.asistenciamedica.ui.recetas.EstudiosViewModel

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}