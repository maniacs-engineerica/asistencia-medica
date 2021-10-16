package com.tp3.asistenciamedica.ui.turnos

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.RecoverySystem
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tp3.asistenciamedica.R
import com.tp3.asistenciamedica.adapters.TurnosAdapters

class TurnosDisponiblesFragment : Fragment() {

    companion object {
        fun newInstance() = TurnosDisponiblesFragment()
    }
    lateinit var v:View
    private lateinit var viewModel: TurnosDisponiblesViewModel
    private lateinit var recTurnos: RecyclerView
    private lateinit var adapter: TurnosAdapters
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var turnos:




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v=inflater.inflate(R.layout.turnos_disponibles_fragment, container, false)




        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TurnosDisponiblesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}