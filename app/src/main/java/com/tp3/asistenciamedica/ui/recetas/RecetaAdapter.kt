package com.tp3.asistenciamedica.ui.recetas

import android.media.Image
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView

class RecetaAdapter: RecyclerView.Adapter <RecetaAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) RecyclerView.ViewHolder(itemView){
        var itemView: ImageView
        var itemTitle: TextView
    }

    val images = intArrayOf(R.drawable)

    init{
        itemImage=itemView.findViewById(R.id.item_image)
        itemTitle= itemView.findViewById(R.id.item_title)
        itemDetail= itemView.findViewById(R.id.item_detal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val v= layoutinflater.from(viewGroup.context).inflate(R.layout.card_layout, viewGroup)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
        viewHolder.imageTitle.text = titles [i]
        viewholder.imageDetail.text= detail[i]
        viewholder.itemImage.setImageResource(images[i])
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

}