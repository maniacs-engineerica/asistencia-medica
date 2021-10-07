package com.tp3.asistenciamedica.ui.historial

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HistorialesViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is historial Fragment"
    }
    val text: LiveData<String> = _text
}