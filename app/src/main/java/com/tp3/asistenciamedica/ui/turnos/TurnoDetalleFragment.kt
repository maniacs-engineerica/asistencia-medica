package com.tp3.asistenciamedica.ui.turnos

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.tp3.asistenciamedica.R

class TurnoDetalleFragment : Fragment() {

    companion object {
        fun newInstance() = TurnoDetalleFragment()
    }

    private lateinit var viewModel: TurnoDetalleViewModel
    private lateinit var txtHorario: TextView
    private lateinit var txtDoctor: TextView
    private lateinit var v: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v=inflater.inflate(R.layout.turno_detalle_fragment, container, false)
        txtHorario= v.findViewById(R.id.txt_horario)
        txtDoctor=v.findViewById(R.id.txt_doctor)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TurnoDetalleViewModel::class.java)
        // TODO: Use the ViewModel
    }

    



}