package com.tp3.asistenciamedica.ui.turnos

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.tp3.asistenciamedica.R
import com.tp3.asistenciamedica.databinding.TurnoFragmentBinding
import com.tp3.asistenciamedica.entities.TurnoStatusEnum
import com.tp3.asistenciamedica.repositories.TurnoRepository
import com.tp3.asistenciamedica.session.Session
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.util.*

class TurnoFragment : Fragment() {

    companion object {
        fun newInstance() = TurnoFragment()
    }
    private lateinit var viewModel: TurnoViewModel
    lateinit var txtFecha: TextView
    lateinit var txtEspecialidad: TextView
    lateinit var txtProfesional: TextView
    lateinit var btnCancelarTurno: Button
    private lateinit var v: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.turno_fragment, container, false)
        txtFecha = v.findViewById(R.id.txt_fecha)
        txtEspecialidad = v.findViewById(R.id.txt_especialidad)
        txtProfesional= v.findViewById(R.id.txt_profesional)
        btnCancelarTurno = v.findViewById(R.id.btn_cancelar)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TurnoViewModel::class.java)
        // TODO: Use the ViewModel
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()
        val usuario = Session.current()
        val parentJob = Job()
        val scope = CoroutineScope(Dispatchers.Default + parentJob)

        scope.launch {
            val turno = TurnoRepository().findTurnoById(
                TurnoFragmentArgs.fromBundle(requireArguments()).turnoId
            )

            withContext(Dispatchers.Main) {
                if (!isAdded) return@withContext
                txtFecha.text = turno?.dateTime?.subSequence(0,10).toString() + " "+ turno?.dateTime?.subSequence(11,16).toString()
                txtProfesional.text= turno?.doctor.toString()
                txtEspecialidad.text = turno?.specialization
            }
            btnCancelarTurno.setOnClickListener {

                scope.launch {

                    turno?.state = TurnoStatusEnum.DISPONIBLE
                    turno?.paciente = usuario
                    if (turno != null) {
                        TurnoRepository().saveTurno(turno)

                    }

                    withContext(Dispatchers.Main) {

                        findNavController().navigate(TurnoFragmentDirections.actionTurnoFragmentToNavigationTurnos())
                    }


                }
                Snackbar.make(v, "Turno Cancelado", Snackbar.LENGTH_LONG).show()
            }


        }

    }



}
