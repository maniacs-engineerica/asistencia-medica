package com.tp3.asistenciamedica.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tp3.asistenciamedica.R
import com.tp3.asistenciamedica.entities.Receta
import java.util.*

class RecetasAdapter(private var recetas: List<Receta> = listOf()) :
    RecyclerView.Adapter<RecetasAdapter.RecetaHolder>() {

    var onRecetaClick: ((Receta) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun swapRecetas(recetas: List<Receta>) {
        this.recetas = recetas
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecetaHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.vh_receta_item, parent, false)
        return RecetaHolder(view)
    }

    override fun onBindViewHolder(holder: RecetaHolder, position: Int) {
        val receta = recetas[position]

        holder.profesionalView.text = receta.profesional
        holder.descripcionView.text = receta.descripcion

        onRecetaClick?.let {
            holder.itemView.setOnClickListener { it(receta) }
        }
    }

    override fun getItemCount(): Int {
        return recetas.size
    }

    class RecetaHolder(v: View) : RecyclerView.ViewHolder(v) {

        var profesionalView: TextView = v.findViewById(R.id.profesional)
        var descripcionView: TextView = v.findViewById(R.id.descripcion)

    }
}