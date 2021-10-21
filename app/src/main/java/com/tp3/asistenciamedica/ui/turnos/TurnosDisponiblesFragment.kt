package com.tp3.asistenciamedica.ui.turnos
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tp3.asistenciamedica.R
import com.tp3.asistenciamedica.adapters.TurnosAdapter

import com.tp3.asistenciamedica.databinding.TurnosDisponiblesFragmentBinding
import com.tp3.asistenciamedica.ui.estudios.EstudiosFragmentDirections


class TurnosDisponiblesFragment : Fragment() {

    companion object {
        fun newInstance() = TurnosDisponiblesFragment()
    }

    private lateinit var turnosViewModel: TurnosDisponiblesViewModel
    private var _binding: TurnosDisponiblesFragmentBinding? = null
    private lateinit var txtEspecialidad: TextView
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: TurnosAdapter





    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        turnosViewModel =
            ViewModelProvider(this).get(TurnosDisponiblesViewModel::class.java)

        _binding = TurnosDisponiblesFragmentBinding.inflate(inflater, container, false)
        txtEspecialidad= binding.root.findViewById(R.id.txt_tituloTurnosDisp)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setupRecycler()
    }

    private fun setupRecycler(){
        adapter = TurnosAdapter()
        adapter.onTurnoClick = {
            onTurnoClick()
        }
       /// binding.recyclerViewTurnosDisp.adapter = adapter
        linearLayoutManager = LinearLayoutManager(context)
        binding.recyclerViewTurnosDisp.adapter=adapter
        binding.recyclerViewTurnosDisp.setHasFixedSize(true)
        binding.recyclerViewTurnosDisp.layoutManager= linearLayoutManager
        turnosViewModel.turnos.observe(viewLifecycleOwner, { result ->
            adapter.swapTurnos(result)
        })
    }


    open fun onTurnoClick() {
        findNavController().navigate(TurnosDisponiblesFragmentDirections.actionTurnosDisponiblesFragmentToTurnoDetalleFragment())
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }




}