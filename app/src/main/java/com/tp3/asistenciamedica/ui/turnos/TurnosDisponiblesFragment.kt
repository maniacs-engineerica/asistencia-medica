package com.tp3.asistenciamedica.ui.turnos
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tp3.asistenciamedica.R
import com.tp3.asistenciamedica.adapters.TurnosAdapter

import com.tp3.asistenciamedica.databinding.TurnosDisponiblesFragmentBinding
import com.tp3.asistenciamedica.entities.Turno
import com.tp3.asistenciamedica.entities.TurnoStatusEnum
import com.tp3.asistenciamedica.entities.UsuarioTypeEnum
import com.tp3.asistenciamedica.repositories.TurnoRepository
import com.tp3.asistenciamedica.session.Session
import com.tp3.asistenciamedica.ui.estudios.EstudiosFragmentDirections
import kotlinx.coroutines.*


class TurnosDisponiblesFragment : Fragment() {

    companion object {
        fun newInstance() = TurnosDisponiblesFragment()
    }

    private lateinit var turnosViewModel: TurnosDisponiblesViewModel
    private var _binding: TurnosDisponiblesFragmentBinding? = null
    private lateinit var txtEspecialidad: TextView
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

    override fun onStart() {
        super.onStart()
        val usuario = Session.current()

        val parentJob = Job()
        val scope = CoroutineScope(Dispatchers.Default + parentJob)

        scope.launch {
            val turnos = if (usuario.tipo == UsuarioTypeEnum.PACIENTE) {
                Log.d("TAG", "Repository request :D")
                ////TurnoRepository().findTurnoByState(TurnoStatusEnum.DISPONIBLE)
                TurnoRepository().findTurnosByEspecialidad(TurnosDisponiblesFragmentArgs.fromBundle(requireArguments()).especialidad,TurnoStatusEnum.DISPONIBLE)
            } else {
                 TurnoRepository().findTurnoByProfesionalId(usuario)
                ///TurnoRepository().findTurnoByState(TurnoStatusEnum.DISPONIBLE)
            }

            Log.d("TAG", "Turnos fetched:"+ turnos)
            withContext(Dispatchers.Main) {
                turnosViewModel.setTurnos(turnos)
            }
        }
    }




    private fun setupRecycler(){
        adapter = TurnosAdapter()
        adapter.onTurnoClick = {
            onTurnoClick(it)
        }
       binding.recyclerViewTurnosDisp.adapter = adapter
        linearLayoutManager = LinearLayoutManager(context)
        binding.recyclerViewTurnosDisp.adapter=adapter
        binding.recyclerViewTurnosDisp.setHasFixedSize(true)
        binding.recyclerViewTurnosDisp.layoutManager= linearLayoutManager

        turnosViewModel.turnos.observe(viewLifecycleOwner, { result ->
            adapter.swapTurnos(result)
        })
    }


    fun onTurnoClick(turno: Turno) {
        findNavController().navigate(TurnosDisponiblesFragmentDirections.actionTurnosDisponiblesFragmentToTurnoDetalleFragment(turno.idTurno))
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }




}