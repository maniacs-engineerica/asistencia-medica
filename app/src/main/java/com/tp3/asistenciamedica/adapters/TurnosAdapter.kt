package com.tp3.asistenciamedica.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tp3.asistenciamedica.R
import com.tp3.asistenciamedica.entities.Turno
import java.text.DateFormat
import java.text.DateFormat.MEDIUM
import java.text.DateFormat.SHORT
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

    override fun onBindViewHolder(holder: TurnoHolder, position: Int) {
        val turno = turnos[position]

        holder.profesionalView.text = turno.profesional
        holder.fechaView.text = formatter.format(Date())

        onTurnoClick?.let {
            holder.itemView.setOnClickListener { it(turno) }
        }
    }

    override fun getItemCount(): Int {
        return turnos.size
    }

    class TurnoHolder(v: View) : RecyclerView.ViewHolder(v) {

        var profesionalView: TextView = v.findViewById(R.id.profesional)
        var fechaView: TextView = v.findViewById(R.id.fecha)

    }
}
