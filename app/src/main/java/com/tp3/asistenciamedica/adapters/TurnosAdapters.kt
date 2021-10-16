package com.tp3.asistenciamedica.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.tp3.asistenciamedica.R
import com.tp3.asistenciamedica.entities.Turno

class TurnosAdapters(var turnosList: MutableList<Turno>,
                    val onItemClick:(Int) -> Unit) : RecyclerView.Adapter<TurnosAdapters.TurnoHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TurnoHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.turnos_disponibles_fragment, parent, false)

        return (TurnoHolder(view))

    }

    override fun onBindViewHolder(holder: TurnosAdapters.TurnoHolder, position: Int) {
        holder.setdescription(turnosList[position].profesional)
        ///holder.setImg(turnosList[position].imageUrl)
        holder.getCardView().setOnClickListener {
            onItemClick(position)
        }


    }

    override fun getItemCount(): Int {
        return turnosList.size
    }

    class TurnoHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View

        init {
            this.view = v
        }

        fun setdescription(description: String) {
            val txt: TextView = view.findViewById(R.id.txt_turno_profesional)
            txt.text = description
        }

        fun getCardView(): CardView {
            return view.findViewById(R.id.turno_cardview)
        }

        /*  fun setImg(url:String) {
              val img: ImageView =view.findViewById(R.id.imgList)
              Glide.with(view).load(url).into(img)
          }*/


    }
}