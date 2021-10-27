package com.tp3.asistenciamedica.ui.turnos

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.tp3.asistenciamedica.adapters.TurnosAdapter
import com.tp3.asistenciamedica.databinding.FragmentTurnosBinding
import com.tp3.asistenciamedica.entities.UsuarioTypeEnum
import com.tp3.asistenciamedica.repositories.RecetaRepository
import com.tp3.asistenciamedica.repositories.TurnoRepository
import com.tp3.asistenciamedica.session.Session
import com.tp3.asistenciamedica.ui.estudios.EstudiosFragmentDirections
import com.tp3.asistenciamedica.ui.recetas.TurnosViewModel
import kotlinx.coroutines.runBlocking

open class TurnosFragment : Fragment() {

    private lateinit var turnosViewModel: TurnosViewModel
    private var _binding: FragmentTurnosBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var adapter: TurnosAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        turnosViewModel =
            ViewModelProvider(this).get(TurnosViewModel::class.java)

        _binding = FragmentTurnosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecycler()
    }

    override fun onStart() {
        super.onStart()

        val usuario = Session.current()

        val turnos = if (usuario.tipo == UsuarioTypeEnum.PACIENTE){
            runBlocking {
                TurnoRepository().findTurnosByPacienteId(usuario.id)
            }
        } else {
            runBlocking {
                TurnoRepository().findTurnoByProfesionalId(usuario.id)
            }
        }
        turnosViewModel.setTurnos(turnos)
    }

    private fun setupRecycler(){
        adapter = TurnosAdapter()
        adapter.onTurnoClick = {
            onTurnoClick()
        }
        binding.turnos.adapter = adapter

        turnosViewModel.turnos.observe(viewLifecycleOwner, { result ->
            adapter.swapTurnos(result)
        })
    }

    open fun onTurnoClick() {
        findNavController().navigate(TurnosFragmentDirections.actionTurnosToTurno())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}