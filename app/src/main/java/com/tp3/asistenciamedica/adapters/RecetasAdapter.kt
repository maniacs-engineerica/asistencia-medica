package com.tp3.asistenciamedica.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tp3.asistenciamedica.R
import com.tp3.asistenciamedica.entities.Receta
import com.tp3.asistenciamedica.entities.UsuarioTypeEnum
import com.tp3.asistenciamedica.session.Session

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

        val usuario = Session.current()

        holder.usuarioView.text = (if (usuario.tipo == UsuarioTypeEnum.PACIENTE)
            receta.doctor else receta.paciente).nombreCompleto
        holder.descripcionView.text = receta.descripcion

        onRecetaClick?.let {
            holder.itemView.setOnClickListener { it(receta) }
        }
    }

    override fun getItemCount(): Int {
        return recetas.size
    }

    class RecetaHolder(v: View) : RecyclerView.ViewHolder(v) {

        var usuarioView: TextView = v.findViewById(R.id.usuario)
        var descripcionView: TextView = v.findViewById(R.id.descripcion)

    }
}