package com.tp3.asistenciamedica.ui.turnos

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.tp3.asistenciamedica.R

class NuevoTurnoFragment : Fragment() {

    companion object {
        fun newInstance() = NuevoTurnoFragment()
    }
    lateinit var v: View
    lateinit var btn_clinica: Button
    lateinit var btn_oftalmologia: Button
    lateinit var btn_cardiologia: Button
    lateinit var btn_hematologia: Button
    lateinit var btn_ginecologia: Button
    lateinit var btn_kinesiologia: Button

    private lateinit var viewModel: NuevoTurnoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v=inflater.inflate(R.layout.nuevo_turno_fragment, container, false)
        btn_clinica= v.findViewById(R.id.btn_clinica)
        btn_oftalmologia= v.findViewById(R.id.btn_oftalmologia)
        btn_cardiologia=v.findViewById(R.id.btn_cardiologia)
        btn_hematologia=v.findViewById(R.id.btn_hematologia)
        btn_ginecologia=v.findViewById(R.id.btn_ginecologia)
        btn_kinesiologia=v.findViewById(R.id.btn_kinesiologia)



        return v
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NuevoTurnoViewModel::class.java)
        // TODO: Use the ViewModel
    }

}