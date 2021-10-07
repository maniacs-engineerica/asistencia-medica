package com.tp3.asistenciamedica.ui.historial

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tp3.asistenciamedica.R

class HistorialFragment : Fragment() {

    companion object {
        fun newInstance() = HistorialFragment()
    }

    private lateinit var viewModel: HistorialViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.historial_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HistorialViewModel::class.java)
        // TODO: Use the ViewModel
    }

}