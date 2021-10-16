package com.tp3.asistenciamedica.ui.turnos

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tp3.asistenciamedica.R
import com.tp3.asistenciamedica.adapters.TurnosAdapters
import com.tp3.asistenciamedica.entities.Turno

class TurnosDisponiblesFragment : Fragment() {

    companion object {
        fun newInstance() = TurnosDisponiblesFragment()
    }

    private lateinit var v:View
    private lateinit var viewModel: TurnosDisponiblesViewModel
    private lateinit var recTurnos: RecyclerView
    private lateinit var adapter: TurnosAdapters
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var turnos= viewModel.getTurnosDisp()





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v=inflater.inflate(R.layout.turnos_disponibles_fragment, container, false)
        recTurnos=v.findViewById(R.id.recyclerViewTurnosDisp)



        return v
    }



    fun onItemClick(position:Int) {
        val action= TurnosDisponiblesFragmentDirections.actionTurnosDisponiblesFragmentToTurnoDetalleFragment(position)
        findNavController().navigate(action)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TurnosDisponiblesViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun OnStart() {
        super.onStart()

        recTurnos.setHasFixedSize(true)
        linearLayoutManager= LinearLayoutManager(context)
        recTurnos.layoutManager= linearLayoutManager
        recTurnos.adapter= TurnosAdapters(turnos) { pos ->
            onItemClick(pos)

        }

    }

}