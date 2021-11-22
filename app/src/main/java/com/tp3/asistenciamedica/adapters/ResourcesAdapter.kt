package com.tp3.asistenciamedica.adapters

import android.content.Context
import android.media.Image
import android.net.Uri
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.tp3.asistenciamedica.R
import com.tp3.asistenciamedica.entities.Resource
import com.tp3.asistenciamedica.repositories.StorageRepository
import com.tp3.asistenciamedica.utils.GlideApp
import java.util.ArrayList

class ResourcesAdapter(private val context: Context) :
    RecyclerView.Adapter<ResourcesAdapter.ViewHolder>() {

    private val storage = Firebase.storage(StorageRepository.FOLDER)

    private var resources: List<Resource> = listOf()

    var callback: Callback? = null

    interface Callback {
        fun onResourceClick(resource: Resource)
    }

    fun swapResources(resources: List<Resource>) {
        this.resources = resources
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.vh_resource_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val resource: Resource = resources[position]
        holder.textView.text = resource.name

        callback?.let { c ->
            holder.itemView.setOnClickListener { c.onResourceClick(resource) }
        }

        if (resource.isPending) {
            holder.imageView.visibility = INVISIBLE
            holder.loadingView.visibility = VISIBLE
        } else {
            holder.imageView.visibility = VISIBLE
            holder.loadingView.visibility = GONE
            val reference = storage.getReference(resource.name)

            GlideApp.with(context)
                .load(reference)
                .into(holder.imageView)
        }
    }

    override fun getItemCount(): Int {
        return resources.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var imageView: ImageView = view.findViewById(R.id.image)
        var textView: TextView = view.findViewById(R.id.text)
        var loadingView: ProgressBar = view.findViewById(R.id.loading)

    }
}