package com.tp3.asistenciamedica.ui.doctor

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.tp3.asistenciamedica.R
import com.tp3.asistenciamedica.databinding.FragmentDoctorInicioBinding
import com.tp3.asistenciamedica.entities.TurnoStatusEnum
import com.tp3.asistenciamedica.repositories.TurnoRepository
import com.tp3.asistenciamedica.session.Session
import com.tp3.asistenciamedica.ui.LoginActivity
import kotlinx.coroutines.*
import java.time.ZonedDateTime

class InicioDoctorFragment : Fragment() {

    private var turnoRepository: TurnoRepository = TurnoRepository()

    private lateinit var inicioDoctorViewModel: InicioDoctorViewModel
    private var _binding: FragmentDoctorInicioBinding? = null


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        inicioDoctorViewModel =
            ViewModelProvider(this).get(InicioDoctorViewModel::class.java)

        _binding = FragmentDoctorInicioBinding.inflate(inflater, container, false)

        setupTurnosOnCards()
        setupLabels()


        return binding.root
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

                inicioDoctorViewModel.setTurnosDisponibles(turnosDisponibles.size.toString())
                inicioDoctorViewModel.setTurnosReservados(turnosReservados.size.toString())


                if (turnosDisponibles.isNotEmpty()) {
                    val lastTurnoDisponible = ZonedDateTime.parse(turnosDisponibles.last().dateTime)

                    inicioDoctorViewModel.setUltimoTurnoDisponible(""+ lastTurnoDisponible.dayOfMonth + "/" + lastTurnoDisponible.monthValue)


                }
                else {
                    inicioDoctorViewModel.setUltimoTurnoDisponible("No disponible")
                }

                if (turnosReservados.isNotEmpty()) {
                    val lastTurnoReservado = ZonedDateTime.parse( turnosReservados.last().dateTime)
                    inicioDoctorViewModel.setUltimoTurnoReservado(""+ lastTurnoReservado.dayOfMonth + "/" + lastTurnoReservado.monthValue)


                    if (turnosReservados.size > 1) {
                        val timeTurno2 = ZonedDateTime.parse(turnosReservados.get(index=1).dateTime)

                        inicioDoctorViewModel.setSegundoTurno(""+timeTurno2.hour+":"+ timeTurno2.minute + " "+ turnosReservados.get(index=1).paciente?.nombreCompleto)
                        inicioDoctorViewModel.setSegundoTurnoId(turnosReservados.get(index=1).idTurno)

                        val timeTurno3 = ZonedDateTime.parse(turnosReservados.get(index=3).dateTime)
                        inicioDoctorViewModel.setTercerTurno(""+timeTurno3.hour+":"+ timeTurno3.minute + " "+ turnosReservados.get(index=3).paciente?.nombreCompleto)
                        inicioDoctorViewModel.setTercerTurnoId(turnosReservados.get(index=3).idTurno)
                    }

                    val timeTurno1 = ZonedDateTime.parse(turnosReservados.get(index=0).dateTime)

                    inicioDoctorViewModel.setPrimerTurno(""+timeTurno1.hour+":"+ timeTurno1.minute + " "+ turnosReservados.get(index=0).paciente?.nombreCompleto)
                    inicioDoctorViewModel.setPrimerTurnoId(turnosReservados.get(index = 0).idTurno)
                }
                else {
                    binding.btnPrimerTurno.visibility = INVISIBLE
                    binding.btnSegundoTurno.visibility = INVISIBLE
                    binding.btnTercerTurno.visibility = INVISIBLE
                    binding.txtUltimoTurnoReservado.text = "N/A"


                    inicioDoctorViewModel.setUltimoTurnoReservado("No disponible")
                }
            }

        }

    }


    private fun setupTurnosOnCards() {
        inicioDoctorViewModel.primerTurno.observe(viewLifecycleOwner, {
            if (it != "Cargando..." && it != "") {

                binding.btnPrimerTurno.visibility = VISIBLE
                binding.btnPrimerTurno.text = it

            }
            else {
                binding.btnPrimerTurno.visibility = INVISIBLE
            }
        })

        inicioDoctorViewModel.segundoTurno.observe(viewLifecycleOwner, {
            if (it != "Cargando..." && it != "") {

                binding.btnSegundoTurno.visibility = VISIBLE
                binding.btnSegundoTurno.text = it

            }
            else {
                binding.btnSegundoTurno.visibility = INVISIBLE
            }
        })

        inicioDoctorViewModel.tercerTurno.observe(viewLifecycleOwner, {
            if (it != "Cargando..." && it != "") {

                binding.btnTercerTurno.visibility = VISIBLE
                binding.btnTercerTurno.text = it

            }
            else {
                binding.btnTercerTurno.visibility = INVISIBLE
            }
        })

        inicioDoctorViewModel.primerTurnoId.observe(viewLifecycleOwner, { idTurno ->
            if (idTurno != "") {
                binding.btnPrimerTurno.setOnClickListener{
                    findNavController().navigate(InicioDoctorFragmentDirections.actionNavigationDoctorInicioToNavigationDoctorTurnoPaciente(
                        idTurno
                    ))
                }
            }
        })

        inicioDoctorViewModel.segundoTurnoId.observe(viewLifecycleOwner, { idTurno ->
            if (idTurno != "") {
                binding.btnSegundoTurno.setOnClickListener{
                    findNavController().navigate(InicioDoctorFragmentDirections.actionNavigationDoctorInicioToNavigationDoctorTurnoPaciente(idTurno))
                }
            }
        })


        inicioDoctorViewModel.tercerTurnoId.observe(viewLifecycleOwner, { idTurno ->
            if (idTurno != "") {
                binding.btnTercerTurno.setOnClickListener{
                    findNavController().navigate(InicioDoctorFragmentDirections.actionNavigationDoctorInicioToNavigationDoctorTurnoPaciente(idTurno))
                }
            }
        })
    }


    private fun setupLabels() {
        inicioDoctorViewModel.turnosDisponibles.observe(viewLifecycleOwner, {
            binding.txtTurnoDisponible.text = it
        })

        inicioDoctorViewModel.turnosReservados.observe(viewLifecycleOwner, {
            binding.txtTurnoReservados.text = it
        })

        inicioDoctorViewModel.ultimoTurnoDisponible.observe(viewLifecycleOwner, {
            binding.txtUltimoTurnoDisponible.text = it
        })

        inicioDoctorViewModel.ultimoTurnoReservado.observe(viewLifecycleOwner, {
            binding.txtUltimoTurnoReservado.text = it
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}