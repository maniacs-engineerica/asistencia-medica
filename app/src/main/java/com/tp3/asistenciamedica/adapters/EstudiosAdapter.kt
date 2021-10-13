package com.tp3.asistenciamedica.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tp3.asistenciamedica.R
import com.tp3.asistenciamedica.entities.Estudio

class EstudiosAdapter(private var estudios: List<Estudio> = listOf()) :
    RecyclerView.Adapter<EstudiosAdapter.EstudioHolder>() {

    var onEstudioClick: ((Estudio) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun swapEstudios(estudios: List<Estudio>) {
        this.estudios = estudios
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstudioHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.vh_estudio_item, parent, false)
        return EstudioHolder(view)
    }

    override fun onBindViewHolder(holder: EstudioHolder, position: Int) {
        val estudio = estudios[position]
        holder.nombreView.text = estudio.nombre

        onEstudioClick?.let {
            holder.itemView.setOnClickListener { it(estudio) }
        }
    }

    override fun getItemCount(): Int {
        return estudios.size
    }

    class EstudioHolder(v: View) : RecyclerView.ViewHolder(v) {

        var nombreView: TextView = v.findViewById(R.id.nombre)

    }
}