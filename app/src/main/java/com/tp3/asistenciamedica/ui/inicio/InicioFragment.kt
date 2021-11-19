package com.tp3.asistenciamedica.ui.inicio

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.View.GONE
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.tp3.asistenciamedica.R
import com.tp3.asistenciamedica.adapters.WidgetAdapter
import com.tp3.asistenciamedica.databinding.FragmentInicioBinding
import com.tp3.asistenciamedica.entities.Estudio
import com.tp3.asistenciamedica.entities.Turno
import com.tp3.asistenciamedica.repositories.EstudioRepository
import com.tp3.asistenciamedica.repositories.TurnoRepository
import com.tp3.asistenciamedica.session.Session
import com.tp3.asistenciamedica.ui.LoginActivity
import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.util.*

class InicioFragment : Fragment() {

    private lateinit var inicioViewModel: InicioViewModel
    private var _binding: FragmentInicioBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val dayFormatter = SimpleDateFormat("dd", Locale.getDefault())
    private val monthFormatter = SimpleDateFormat("MMM", Locale.getDefault())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        inicioViewModel =
            ViewModelProvider(this).get(InicioViewModel::class.java)

        _binding = FragmentInicioBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)

        setupTurnos()
        setupEstudios()
        setupHistoria()
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

    @SuppressLint("NewApi")
    private fun setupTurnos() {
        val user = Session.current()
        val turnosAdapter = WidgetAdapter<Turno>()
        turnosAdapter.onLoadItems = onLoadItems@{
            return@onLoadItems TurnoRepository().findTurnosByPacienteId(user.id)
        }
        turnosAdapter.onLoaded = {
            binding.loadingTurnos.visibility = GONE
        }
        turnosAdapter.onBindViewHolder = { turno, holder ->
            val date = Date.from(ZonedDateTime.parse(turno.dateTime).toInstant())
            holder.dayView.text = dayFormatter.format(date)
            holder.monthView.text = monthFormatter.format(date).uppercase(Locale.getDefault())
            holder.descriptionView.text = "${turno.doctor.nombreCompleto}\n${turno.specialization}"
        }
        turnosAdapter.onItemClick = {
            findNavController().navigate(InicioFragmentDirections.actionNavigationInicioToTurno(it.idTurno, "home"))
        }
        binding.turnos.adapter = turnosAdapter
        turnosAdapter.load()
    }

    @SuppressLint("NewApi")
    private fun setupEstudios() {
        val user = Session.current()
        val estudiosAdapter = WidgetAdapter<Estudio>()
        estudiosAdapter.onLoadItems = onLoadItems@{
            return@onLoadItems EstudioRepository().findEstudiosHistoriaByPacientId(user.id)
        }
        estudiosAdapter.onLoaded = {
            binding.loadingEstudios.visibility = GONE
        }
        estudiosAdapter.onBindViewHolder = { estudio, holder ->
            holder.dayView.text = dayFormatter.format(estudio.fecha)
            holder.monthView.text =
                monthFormatter.format(estudio.fecha).uppercase(Locale.getDefault())
            holder.descriptionView.text = "${estudio.nombre}\n${estudio.doctor.nombreCompleto}"
        }
        estudiosAdapter.onItemClick = {
            findNavController().navigate(InicioFragmentDirections.actionNavigationInicioToEstudio(it.idEstudio, "home"))
        }
        binding.estudios.adapter = estudiosAdapter
        estudiosAdapter.load()
    }

    @SuppressLint("NewApi")
    private fun setupHistoria() {
        val user = Session.current()
        val historiaAdapter = WidgetAdapter<Turno>()
        historiaAdapter.onLoadItems = onLoadItems@{
            return@onLoadItems TurnoRepository().findHistorialByPacienteId(user.id)
        }
        historiaAdapter.onLoaded = {
            binding.loadingHistorias.visibility = GONE
        }
        historiaAdapter.onBindViewHolder = { turno, holder ->
            val date = Date.from(ZonedDateTime.parse(turno.dateTime).toInstant())
            holder.dayView.text = dayFormatter.format(date)
            holder.monthView.text = monthFormatter.format(date).uppercase(Locale.getDefault())
            holder.descriptionView.text = "${turno.doctor.nombreCompleto}\n${turno.specialization}"
        }
        historiaAdapter.onItemClick = {
            findNavController().navigate(InicioFragmentDirections.actionNavigationInicioToTurno(it.idTurno, "home"))
        }
        binding.historias.adapter = historiaAdapter
        historiaAdapter.load()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

