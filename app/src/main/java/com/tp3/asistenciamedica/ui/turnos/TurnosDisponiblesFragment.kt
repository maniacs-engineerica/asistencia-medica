package com.tp3.asistenciamedica.ui.turnos

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
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
import com.tp3.asistenciamedica.ui.estudios.EstudioFragmentDirections
import com.tp3.asistenciamedica.ui.estudios.EstudiosFragmentDirections
import kotlinx.coroutines.*


class TurnosDisponiblesFragment : Fragment() {

    companion object {
        fun newInstance() = TurnosDisponiblesFragment()
    }

    private lateinit var turnosViewModel: TurnosDisponiblesViewModel
    private var _binding: TurnosDisponiblesFragmentBinding? = null

    private val binding get() = _binding!!
    private lateinit var adapter: TurnosAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        turnosViewModel =
            ViewModelProvider(this).get(TurnosDisponiblesViewModel::class.java)

        _binding = TurnosDisponiblesFragmentBinding.inflate(inflater, container, false)



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        setupRecycler()
    }

    override fun onStart() {
        super.onStart()

        val parentJob = Job()
        val scope = CoroutineScope(Dispatchers.Default + parentJob)

        binding.turnosDisponibles.showShimmerAdapter()

        scope.launch {
            val especialidad =
                TurnosDisponiblesFragmentArgs.fromBundle(requireArguments()).especialidad
            val turnos =
                TurnoRepository().findTurnosByEspecialidad(especialidad, TurnoStatusEnum.DISPONIBLE)

            Log.d("TAG", "Turnos fetched:" + turnos)
            withContext(Dispatchers.Main) {
                if (!isAdded) return@withContext
                if (turnos.isEmpty()) {
                    Snackbar.make(
                        binding.root,
                        "No hay turnos para esa Especialidad Disponibles",
                        Snackbar.LENGTH_LONG
                    ).show()
                    findNavController().navigate(TurnosDisponiblesFragmentDirections.actionTurnosDisponiblesFragmentToNavigationTurnos())
                    return@withContext
                }
                turnosViewModel.setTurnos(turnos)
                binding.turnosDisponibles.hideShimmerAdapter()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                findNavController().navigate(TurnosDisponiblesFragmentDirections.actionTurnosDisponiblesFragmentToNuevoTurnoFragment())
            }
        }
        return true
    }

    private fun setupRecycler() {
        adapter = TurnosAdapter()
        adapter.onTurnoClick = {
            onTurnoClick(it)
        }
        binding.turnosDisponibles.adapter = adapter

        turnosViewModel.turnos.observe(viewLifecycleOwner, { result ->
            adapter.swapTurnos(result)
        })
    }


    fun onTurnoClick(turno: Turno) {
        findNavController().navigate(
            TurnosDisponiblesFragmentDirections.actionTurnosDisponiblesFragmentToTurnoDetalleFragment(
                turno.idTurno
            )
        )
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}