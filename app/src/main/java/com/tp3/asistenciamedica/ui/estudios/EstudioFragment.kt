package com.tp3.asistenciamedica.ui.estudios

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toFile
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.map
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.tp3.asistenciamedica.R
import com.tp3.asistenciamedica.adapters.ResourcesAdapter
import com.tp3.asistenciamedica.daos.EstudioDao
import com.tp3.asistenciamedica.databinding.EstudioFragmentBinding
import com.tp3.asistenciamedica.entities.Resource
import com.tp3.asistenciamedica.entities.Usuario
import com.tp3.asistenciamedica.entities.UsuarioTypeEnum
import com.tp3.asistenciamedica.repositories.EstudioRepository
import com.tp3.asistenciamedica.repositories.RecetaRepository
import com.tp3.asistenciamedica.repositories.StorageRepository
import com.tp3.asistenciamedica.repositories.UsuarioRepository
import com.tp3.asistenciamedica.session.Session
import com.tp3.asistenciamedica.ui.BaseActivity
import com.tp3.asistenciamedica.ui.recetas.RecetaFragmentArgs
import com.tp3.asistenciamedica.ui.recetas.RecetaFragmentDirections
import com.tp3.asistenciamedica.utils.DateUtils
import com.tp3.asistenciamedica.utils.ImagePicker
import com.tp3.asistenciamedica.utils.MyFileProvider
import com.tp3.asistenciamedica.utils.ProgressUtils
import kotlinx.coroutines.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class EstudioFragment : Fragment() {

    private val storage = Firebase.storage(StorageRepository.FOLDER)

    private var _binding: EstudioFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: EstudioViewModel

    private lateinit var adapter: ResourcesAdapter


    private val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    val estudioId: String?
        get() = EstudioFragmentArgs.fromBundle(requireArguments()).estudioId.takeIf { it != "-1" }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProvider(this).get(EstudioViewModel::class.java)

        _binding = EstudioFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        setupRecycler()


        val usuario = Session.current()

        val exists = estudioId != null

        if (usuario.tipo == UsuarioTypeEnum.PACIENTE || exists) {
            binding.paciente.isEnabled = false
            binding.fecha.isEnabled = false
            binding.estudio.isEnabled = false
            binding.resultado.isEnabled = false
            binding.subir.visibility = View.GONE
            binding.subirArchivo.visibility = View.GONE
        } else {
            binding.profesional.setSelectedItem(usuario)
            lifecycleScope.launch {
                binding.paciente.options = UsuarioRepository().allPacientes()
            }


        }

        binding.subir.setOnClickListener { subir() }
        binding.subirArchivo.setOnClickListener { adjuntar() }

        viewModel.estudio.observe(viewLifecycleOwner, { result ->
            binding.paciente.setSelectedItem(result.paciente)
            binding.profesional.setSelectedItem(result.doctor)
            binding.estudio.setText(result.nombre)
            binding.resultado.setText(result.resultado)
            binding.fecha.setText(formatter.format(result.fecha))
        })
        viewModel.resources.observe(viewLifecycleOwner, { result ->
            adapter.swapResources(result)
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                findNavController().popBackStack()
            }
        }
        return true
    }


    override fun onStart() {
        super.onStart()

        val id = estudioId ?: return

        val parentJob = Job()
        val scope = CoroutineScope(Dispatchers.Default + parentJob)

        scope.launch {
            val estudio = EstudioRepository().findEstudioById(id) ?: return@launch
            withContext(Dispatchers.Main) {
                if (!isAdded) return@withContext
                viewModel.setEstudio(estudio)
            }
        }
    }

    private fun setupRecycler() {
        binding.resources.layoutManager = GridLayoutManager(context, 2)
        adapter = ResourcesAdapter(requireContext())
        adapter.callback = object : ResourcesAdapter.Callback {
            override fun onResourceClick(resource: Resource) {
                if (Session.current().tipo == UsuarioTypeEnum.PACIENTE) {
                    download(resource)
                }
            }
        }
        binding.resources.adapter = adapter
    }

    private fun download(resource: Resource) {
        val reference = storage.getReference(resource.name)
        val localFile = File.createTempFile(resource.name, ".jpg")
        ProgressUtils.showLoading(requireContext())
        reference.getFile(localFile).addOnSuccessListener {
            ProgressUtils.hideLoading()
            showImage(localFile)
        }.addOnFailureListener {
            Snackbar.make(binding.subirArchivo, R.string.download_failure, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun showImage(file: File){
        val uri = MyFileProvider.getUriForFile(requireContext(), file)
        val intent = Intent(Intent.ACTION_VIEW)
        intent.flags =
            Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
        intent.setDataAndType(uri, "image/*")
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Snackbar.make(binding.subirArchivo, R.string.application_not_found, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun subir() {
        if (binding.paciente.getSelectedItem() == null) {
            binding.paciente.error = getString(R.string.validacion_paciente)
            return
        }

        if (binding.estudio.text.isEmpty()) {
            binding.estudio.error = getString(R.string.validacion_estudio)
            return
        }

        if (!DateUtils.isValidDate(binding.fecha.text.toString(), "dd/MM/yyyy")) {
            binding.fecha.error = getString(R.string.validacion_fecha)
            return
        }

        if (binding.resultado.text.isEmpty()) {
            binding.resultado.error = getString(R.string.validacion_resultado)
            return
        }

        val estudio = EstudioDao()
        estudio.pacienteId = (binding.paciente.getSelectedItem() as Usuario).id
        estudio.doctorId = (binding.profesional.getSelectedItem() as Usuario).id
        estudio.nombre = binding.estudio.text.toString()
        estudio.fecha = formatter.parse(binding.fecha.text.toString())!!
        estudio.resultado = binding.resultado.text.toString()
        estudio.rutaDeImagenes = viewModel.resources.value!!.map { it.name }

        lifecycleScope.launch {
            EstudioRepository().create(estudio)
            findNavController().navigateUp()
        }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}