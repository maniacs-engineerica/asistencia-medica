package com.tp3.asistenciamedica.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tp3.asistenciamedica.R
import com.tp3.asistenciamedica.entities.Turno
import com.tp3.asistenciamedica.entities.UsuarioTypeEnum
import com.tp3.asistenciamedica.session.Session
import java.text.DateFormat
import java.text.DateFormat.MEDIUM
import java.text.DateFormat.SHORT
import java.time.ZonedDateTime
import java.util.*

class TurnosAdapter(private var turnos: List<Turno> = listOf()) :
    RecyclerView.Adapter<TurnosAdapter.TurnoHolder>() {

    var onTurnoClick: ((Turno) -> Unit)? = null

    val formatter = DateFormat.getDateTimeInstance(MEDIUM, SHORT)

    @SuppressLint("NotifyDataSetChanged")
    fun swapTurnos(turnos: List<Turno>) {
        this.turnos = turnos
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TurnoHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.vh_turno_item, parent, false)
        return TurnoHolder(view)
    }

    @SuppressLint("NewApi")
    override fun onBindViewHolder(holder: TurnoHolder, position: Int) {
        val turno = turnos[position]

        val usuario = Session.current()

        if (usuario.tipo == UsuarioTypeEnum.PACIENTE){
            holder.usuarioView.text = "${turno.doctor.nombreCompleto} - ${turno.specialization}"
        } else {
            holder.usuarioView.text = turno.paciente!!.nombreCompleto
        }

        val date = Date.from(ZonedDateTime.parse(turno.dateTime).toInstant())

        holder.fechaView.text = formatter.format(date)

        onTurnoClick?.let {
            holder.itemView.setOnClickListener { it(turno) }
        }
    }

    override fun getItemCount(): Int {
        return turnos.size
    }

    class TurnoHolder(v: View) : RecyclerView.ViewHolder(v) {

        var usuarioView: TextView = v.findViewById(R.id.usuario)
        var fechaView: TextView = v.findViewById(R.id.fecha)

    }
}