package com.tp3.asistenciamedica.ui.doctor

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.tp3.asistenciamedica.R
import com.tp3.asistenciamedica.databinding.FragmentDoctorInicioBinding
import com.tp3.asistenciamedica.databinding.FragmentInicioBinding
import com.tp3.asistenciamedica.entities.TurnoStatusEnum
import com.tp3.asistenciamedica.repositories.TurnoRepository
import com.tp3.asistenciamedica.session.Session
import com.tp3.asistenciamedica.ui.LoginActivity
import com.tp3.asistenciamedica.ui.inicio.InicioFragmentDirections
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.util.*

class InicioDoctorFragment : Fragment() {

    private var turnoRepository: TurnoRepository = TurnoRepository()

    private lateinit var inicioDoctorViewModel: InicioDoctorViewModel
    private var _binding: FragmentDoctorInicioBinding? = null

    private val dayFormatter = SimpleDateFormat("dd", Locale.getDefault())
    private val monthFormatter = SimpleDateFormat("MMM", Locale.getDefault())
    private lateinit var id1: String
    private lateinit var id0: String

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        inicioDoctorViewModel =
            ViewModelProvider(this).get(InicioDoctorViewModel::class.java)

        _binding = FragmentDoctorInicioBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        //setupInformation()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.inicio_fragment, menu)
        menu.findItem(R.id.logout).setOnMenuItemClickListener {
            Session.logout()
            val intent = Intent(context, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            requireContext().startActivity(intent)
            true
        }
    }

    override fun onStart() {
        super.onStart()
        setupInformation()
    }


    @SuppressLint("NewApi")
    fun setupInformation() {

        val parentJob = Job()
        val scope = CoroutineScope(Dispatchers.Default + parentJob)

        scope.launch {
            val actTurnos =
                turnoRepository.findTurnoByProfesionalId(Session.current())

            val turnosReservados = actTurnos.filter { TurnoStatusEnum.RESERVADO == it.state }
            val turnosDisponibles = actTurnos.filter { TurnoStatusEnum.DISPONIBLE == it.state }


            withContext(Dispatchers.Main) {
                //estudiosViewModel.setEstudios(recetas)
                binding.txtTurnoDisponible.text = turnosDisponibles.size.toString()
                binding.txtTurnoReservados.text = turnosReservados.size.toString()

                //TODO: Send this data to the viewModel

                if (turnosDisponibles.isNotEmpty()) {
                    var lastTurnoDisponible = ZonedDateTime.parse(turnosDisponibles.last().dateTime)

                binding.txtUltimoTurnoDisponible.text = ""+ lastTurnoDisponible.dayOfMonth + "/" + lastTurnoDisponible.monthValue

                }
                else {
                    binding.txtUltimoTurnoDisponible.text = "N/A"
                }

                if (turnosReservados.isNotEmpty()) {
                    var lastTurnoReservado = ZonedDateTime.parse( turnosReservados.last().dateTime)
                    binding.txtUltimoTurnoReservado.text = ""+ lastTurnoReservado.dayOfMonth + "/" + lastTurnoReservado.month

                    if (turnosReservados.size > 1) {
                        var timeTurno2 = ZonedDateTime.parse(turnosReservados.get(index=1).dateTime)
                        binding.btnSegundoTurno.visibility = VISIBLE
                        binding.btnSegundoTurno.text = ""+timeTurno2.hour+":"+ timeTurno2.minute + " "+ turnosReservados.get(index=1).paciente?.nombreCompleto
                    }

                    var timeTurno1 = ZonedDateTime.parse(turnosReservados.get(index=0).dateTime)
                    binding.btnSegundoTurno.visibility = VISIBLE
                    binding.btnSegundoTurno.text = ""+timeTurno1.hour+":"+ timeTurno1.minute + " "+ turnosReservados.get(index=0).paciente?.nombreCompleto

                    id1 = turnosReservados.get(index = 1).idTurno
                    id0 = turnosReservados.get(index = 0).idTurno


                }
                else {
                    binding.btnPrimerTurno.visibility = INVISIBLE
                    binding.btnSegundoTurno.visibility = INVISIBLE
                    binding.txtUltimoTurnoReservado.text = "N/A"
                }

                binding.btnPrimerTurno.setOnClickListener{
                    findNavController().navigate(InicioDoctorFragmentDirections.actionNavigationDoctorInicioToNavigationDoctorTurnoPaciente(id1))
                }

                binding.btnSegundoTurno.setOnClickListener{
                    findNavController().navigate(InicioDoctorFragmentDirections.actionNavigationDoctorInicioToNavigationDoctorTurnoPaciente(id0))
                }


            }

        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}