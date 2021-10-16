package com.tp3.asistenciamedica.ui.turnos

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import com.tp3.asistenciamedica.R

class TurnoFragment : Fragment() {

    companion object {
        fun newInstance() = TurnoFragment()
    }

    private lateinit var viewModel: TurnoViewModel
    lateinit var btn_nuevoturno: Button
    lateinit var v:View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v= inflater.inflate(R.layout.turno_fragment, container, false)
        btn_nuevoturno=v.findViewById(R.id.btn_nuevoturno)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TurnoViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()

        btn_nuevoturno.setOnClickListener{
           val action= TurnoFragmentDirections.actionTurnoFragmentToNuevoTurnoFragment()
            v.findNavController().navigate(action)
        }
    }



}