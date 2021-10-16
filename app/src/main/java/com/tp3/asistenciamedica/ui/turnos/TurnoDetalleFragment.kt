package com.tp3.asistenciamedica.ui.turnos

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tp3.asistenciamedica.R

class TurnoDetalleFragment : Fragment() {

    companion object {
        fun newInstance() = TurnoDetalleFragment()
    }

    private lateinit var viewModel: TurnoDetalleViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.turno_detalle_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TurnoDetalleViewModel::class.java)
        // TODO: Use the ViewModel
    }

}