package com.tp3.asistenciamedica.ui.doctor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.tp3.asistenciamedica.databinding.FragmentDoctorInicioBinding
import com.tp3.asistenciamedica.databinding.FragmentInicioBinding

class InicioDoctorFragment : Fragment() {

    private lateinit var inicioDoctorViewModel: InicioDoctorViewModel
    private var _binding: FragmentDoctorInicioBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        inicioDoctorViewModel =
            ViewModelProvider(this).get(InicioDoctorViewModel::class.java)

        _binding = FragmentDoctorInicioBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}