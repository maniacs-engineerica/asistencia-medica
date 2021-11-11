package com.tp3.asistenciamedica.ui.recetas

import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.tp3.asistenciamedica.R
import com.tp3.asistenciamedica.adapters.ResourcesAdapter
import com.tp3.asistenciamedica.daos.RecetaDao
import com.tp3.asistenciamedica.databinding.FragmentRecetasBinding
import com.tp3.asistenciamedica.databinding.RecetaFragmentBinding
import com.tp3.asistenciamedica.entities.Receta
import com.tp3.asistenciamedica.entities.Resource
import com.tp3.asistenciamedica.entities.Usuario
import com.tp3.asistenciamedica.entities.UsuarioTypeEnum
import com.tp3.asistenciamedica.repositories.RecetaRepository
import com.tp3.asistenciamedica.repositories.StorageRepository
import com.tp3.asistenciamedica.repositories.UsuarioRepository
import com.tp3.asistenciamedica.session.Session
import com.tp3.asistenciamedica.ui.BaseActivity
import com.tp3.asistenciamedica.utils.DateUtils
import com.tp3.asistenciamedica.utils.ImagePicker
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

class RecetaFragment : Fragment() {

    private var _binding: RecetaFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: RecetaViewModel

    private lateinit var adapter: ResourcesAdapter

    private val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProvider(this).get(RecetaViewModel::class.java)

        _binding = RecetaFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecycler()

        val usuario = Session.current()

        val exists = RecetaFragmentArgs.fromBundle(requireArguments()).recetaId != null

        if (usuario.tipo == UsuarioTypeEnum.PACIENTE || exists) {
            binding.paciente.isEnabled = false
            binding.fecha.isEnabled = false
            binding.descripcion.isEnabled = false
            binding.emitir.visibility = GONE
            binding.subirArchivo.visibility = GONE
        } else {
            binding.profesional.setSelectedItem(usuario)
            lifecycleScope.launch {
                binding.paciente.options = UsuarioRepository().allPacientes()
            }
        }

        binding.emitir.setOnClickListener { emitirReceta() }
        binding.subirArchivo.setOnClickListener { adjuntar() }

        viewModel.receta.observe(viewLifecycleOwner, { result ->
            binding.paciente.setSelectedItem(result.paciente)
            binding.profesional.setSelectedItem(result.doctor)
            binding.descripcion.setText(result.descripcion)
            binding.fecha.setText(formatter.format(result.fecha))
        })
        viewModel.resources.observe(viewLifecycleOwner, { result ->
            adapter.swapResources(result)
        })
    }

    override fun onStart() {
        super.onStart()

        val id = RecetaFragmentArgs.fromBundle(requireArguments()).recetaId ?: return

        val parentJob = Job()
        val scope = CoroutineScope(Dispatchers.Default + parentJob)

        scope.launch {
            val receta = RecetaRepository().findRecetaById(id) ?: return@launch
            withContext(Dispatchers.Main){
                viewModel.setReceta(receta)
            }
        }
    }

    private fun setupRecycler() {
        binding.resources.layoutManager = GridLayoutManager(context, 2)
        adapter = ResourcesAdapter(requireContext())
        adapter.callback = object : ResourcesAdapter.Callback {
            override fun onResourceClick(resource: Resource) {

            }
        }
        binding.resources.adapter = adapter
    }

    private fun adjuntar() {
        ImagePicker(activity as BaseActivity).pick { uri ->
            uri?.let { upload(it) }
        }
    }

    private fun upload(uri: Uri) {

        val parentJob = Job()
        val scope = CoroutineScope(Dispatchers.Default + parentJob)

        scope.launch {
            try {
                val name = uri.pathSegments.last()
                val resource = Resource(name).apply {
                    isPending = true
                }
                val resources = viewModel.resources.value?.toMutableList() ?: mutableListOf()
                resources.add(resource)
                withContext(Dispatchers.Main) {
                    viewModel.setResources(resources)
                }
                StorageRepository().upload(uri, name)
                resource.isPending = false
                withContext(Dispatchers.Main) {
                    adapter.notifyDataSetChanged()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Snackbar.make(binding.subirArchivo, e.message ?: "error", 3000).show()
                }
            }
        }
    }

    private fun emitirReceta() {
        if (binding.paciente.getSelectedItem() == null){
            binding.paciente.error = getString(R.string.validacion_paciente)
            return
        }

        if (!DateUtils.isValidDate(binding.fecha.text.toString(), "dd/MM/yyyy")){
            binding.fecha.error = getString(R.string.validacion_fecha)
            return
        }

        if (binding.descripcion.text.isEmpty()){
            binding.descripcion.error = getString(R.string.validacion_receta)
            return
        }

        val receta = RecetaDao()
        receta.pacienteId = (binding.paciente.getSelectedItem() as Usuario).id
        receta.doctorId = (binding.profesional.getSelectedItem() as Usuario).id
        receta.fecha = formatter.parse(binding.fecha.text.toString())!!
        receta.descripcion = binding.descripcion.text.toString()
        receta.rutaDeImagenes = viewModel.resources.value!!.map { it.name }

        lifecycleScope.launch {
            RecetaRepository().create(receta)
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}