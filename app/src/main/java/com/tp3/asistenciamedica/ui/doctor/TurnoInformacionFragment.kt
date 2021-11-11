package com.tp3.asistenciamedica.ui.doctor

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.tp3.asistenciamedica.R
import com.tp3.asistenciamedica.daos.RecetaDao
import com.tp3.asistenciamedica.daos.TurnoDao
import com.tp3.asistenciamedica.databinding.FragmentAnadirinfoBinding
import com.tp3.asistenciamedica.entities.Usuario
import com.tp3.asistenciamedica.entities.UsuarioTypeEnum
import com.tp3.asistenciamedica.repositories.RecetaRepository
import com.tp3.asistenciamedica.repositories.TurnoRepository
import com.tp3.asistenciamedica.repositories.UsuarioRepository
import com.tp3.asistenciamedica.session.Session
import com.tp3.asistenciamedica.ui.recetas.RecetaFragmentArgs
import com.tp3.asistenciamedica.utils.DateUtils
import kotlinx.coroutines.*
import java.time.ZonedDateTime
import java.util.*

class TurnoInformacionFragment : Fragment() {

    private lateinit var turnoInformacionViewModel: TurnoInformacionViewModel
    private lateinit var id: String
    private var _binding: FragmentAnadirinfoBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        turnoInformacionViewModel =
            ViewModelProvider(this).get(TurnoInformacionViewModel::class.java)

        _binding = FragmentAnadirinfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NewApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.edtInfo.setOnClickListener { agregarInformacion() }
    }

    override fun onStart() {
        super.onStart()

        id = TurnoInformacionFragmentArgs.fromBundle(requireArguments()).turnoId ?: return

        val parentJob = Job()
        val scope = CoroutineScope(Dispatchers.Default + parentJob)

        scope.launch {
            val turno = TurnoRepository().findTurnoById(id) ?: return@launch
            withContext(Dispatchers.Main){
                turnoInformacionViewModel.setTurno(turno)
            }
        }
    }

    private fun agregarInformacion() {

        if (binding.txtAnInfo.text.isEmpty()){
            binding.txtAnInfo.error = getString(R.string.validacion_descripcion)
            return
        }

        val turno = TurnoDao()
        turno.detail = binding.edtInfo.text.toString()
        turno.idTurno = id

        lifecycleScope.launch {
            TurnoRepository().saveTurnoDao(turno)
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}