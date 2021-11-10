package com.tp3.asistenciamedica.ui.turnos

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.tp3.asistenciamedica.R
import com.tp3.asistenciamedica.repositories.TurnoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class TurnoDetalleFragment : Fragment() {

    companion object {
        fun newInstance() = TurnoDetalleFragment()
    }

    private lateinit var viewModel: TurnoDetalleViewModel
    lateinit var txtFecha: TextView
    lateinit var txtEspecialidad: TextView
    lateinit var btnSolicitarTurno: Button
    private lateinit var v: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v=inflater.inflate(R.layout.turno_detalle_fragment, container, false)
        txtFecha= v.findViewById(R.id.txt_fecha)
        txtEspecialidad= v.findViewById(R.id.txt_especialidad)
        btnSolicitarTurno= v.findViewById(R.id.btn_solicitar)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TurnoDetalleViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()
        val parentJob = Job()
        val scope = CoroutineScope(Dispatchers.Default + parentJob)

        scope.launch {
            val turno=TurnoRepository().findTurnoByDocumentId(TurnoDetalleFragmentArgs.fromBundle(requireArguments()).turnoId)

            txtFecha.text= turno?.dateTime
            txtEspecialidad.text=turno?.specialization


        ///// modificar estado
         /*   btnSolicitarTurno.setOnClickListener{
                turno?.state?.nextStatus()
            }*/


        }




    }



}