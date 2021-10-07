package com.tp3.asistenciamedica.ui.historial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.tp3.asistenciamedica.databinding.FragmentHistorialBinding

class HistorialesFragment : Fragment() {

    private lateinit var historialesViewModel: HistorialesViewModel
    private var _binding: FragmentHistorialBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        historialesViewModel =
            ViewModelProvider(this).get(HistorialesViewModel::class.java)

        _binding = FragmentHistorialBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}