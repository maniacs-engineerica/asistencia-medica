package com.tp3.asistenciamedica.ui.estudios

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tp3.asistenciamedica.R
import com.tp3.asistenciamedica.databinding.FragmentEstudiosBinding

class EstudioFragment : Fragment() {

    companion object {
        fun newInstance() = EstudioFragment()
    }

    private lateinit var viewModel: EstudioViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.estudio_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(EstudioViewModel::class.java)
        // TODO: Use the ViewModel
    }

}