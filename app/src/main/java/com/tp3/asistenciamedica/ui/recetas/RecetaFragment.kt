package com.tp3.asistenciamedica.ui.recetas

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tp3.asistenciamedica.R

class RecetaFragment : Fragment() {

    companion object {
        fun newInstance() = RecetaFragment()
    }

    private lateinit var viewModel: RecetaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.receta_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RecetaViewModel::class.java)
        // TODO: Use the ViewModel
    }

}