package com.tp3.asistenciamedica.ui.doctor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InicioDoctorViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is inicio Fragment"
    }
    val text: LiveData<String> = _text
}