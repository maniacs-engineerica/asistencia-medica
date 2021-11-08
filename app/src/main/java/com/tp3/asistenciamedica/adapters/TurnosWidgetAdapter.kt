package com.tp3.asistenciamedica.adapters

import android.annotation.SuppressLint
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.tp3.asistenciamedica.AsistenciaMedicaApplication
import com.tp3.asistenciamedica.R
import com.tp3.asistenciamedica.entities.Turno
import com.tp3.asistenciamedica.repositories.TurnoRepository
import com.tp3.asistenciamedica.session.Session
import kotlinx.coroutines.*
import java.text.DateFormat
import java.text.DateFormat.MEDIUM
import java.text.DateFormat.SHORT
import java.util.*

class WidgetAdapter<T>() : RecyclerView.Adapter<WidgetAdapter.WidgetHolder>() {

    private var items: List<T> = listOf()

    var onItemClick: ((T) -> Unit)? = null

    var onLoadItems: (suspend () -> List<T>)? = null

    var onBindViewHolder: ((T, WidgetHolder) -> Unit)? = null

    fun load() {
        onLoadItems?.let {
            val parentJob = Job()
            val scope = CoroutineScope(Dispatchers.Default + parentJob)
            scope.launch {
                val items = it()
                withContext(Dispatchers.Main) {
                    swapItems(items)
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun swapItems(items: List<T>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WidgetHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.vh_widget_item, parent, false)
        return WidgetHolder(view)
    }

    override fun onBindViewHolder(holder: WidgetHolder, position: Int) {
        val item = items[position]

        onItemClick?.let {
            holder.itemView.setOnClickListener { it(item) }
        }

        val oval = ShapeDrawable(OvalShape())
        oval.paint.color = ContextCompat.getColor(
            AsistenciaMedicaApplication.applicationContext(),
            R.color.teal_700
        )
        holder.bubbleView.background = oval

        onBindViewHolder?.let {
            it(item, holder)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class WidgetHolder(v: View) : RecyclerView.ViewHolder(v) {
        var bubbleView: View = v.findViewById(R.id.bubble)
        var dayView: TextView = v.findViewById(R.id.day)
        var monthView: TextView = v.findViewById(R.id.month)
        var descriptionView: TextView = v.findViewById(R.id.description)
    }
}