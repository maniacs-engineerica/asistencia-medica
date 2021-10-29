package com.tp3.asistenciamedica.ui.inicio

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.tp3.asistenciamedica.R
import com.tp3.asistenciamedica.databinding.FragmentInicioBinding
import com.tp3.asistenciamedica.session.Session
import com.tp3.asistenciamedica.ui.LoginActivity
import com.tp3.asistenciamedica.ui.MainActivity

class InicioFragment : Fragment() {

    private lateinit var inicioViewModel: InicioViewModel
    private var _binding: FragmentInicioBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        inicioViewModel =
            ViewModelProvider(this).get(InicioViewModel::class.java)

        _binding = FragmentInicioBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.inicio_fragment, menu)
        menu.findItem(R.id.logout).setOnMenuItemClickListener {
            Session.logout()
            val intent = Intent(context, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            requireContext().startActivity(intent)
            true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}