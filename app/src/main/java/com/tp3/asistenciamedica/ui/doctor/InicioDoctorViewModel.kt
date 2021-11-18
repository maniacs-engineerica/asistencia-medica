package com.tp3.asistenciamedica.ui.doctor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.ZonedDateTime

class InicioDoctorViewModel : ViewModel() {



    private var _turnosDisponibles = MutableLiveData<String>().apply {
        value = "Cargando..."
    }

    val turnosDisponibles: LiveData<String> = _turnosDisponibles

    private var _turnosReservados = MutableLiveData<String>().apply {
        value = "Cargando..."
    }

    val turnosReservados: LiveData<String> = _turnosReservados


    private var _ultimoTurnoReservado = MutableLiveData<String>().apply {
        value = "Cargando..."
    }

    val ultimoTurnoReservado: LiveData<String> = _ultimoTurnoReservado

    private var _ultimoTurnoDisponible = MutableLiveData<String>().apply {
        value = "Cargando..."
    }

    val ultimoTurnoDisponible: LiveData<String> = _ultimoTurnoDisponible


    private var _primerTurno = MutableLiveData<String>().apply {
        value = "Cargando..."
    }

    val primerTurno: LiveData<String> = _primerTurno

    private var _segundoTurno = MutableLiveData<String>().apply {
        value = "Cargando..."
    }

    val segundoTurno: LiveData<String> = _segundoTurno

    private var _tercerTurno = MutableLiveData<String>().apply {
        value = "Cargando..."
    }

    val tercerTurno: LiveData<String> = _tercerTurno


    private var _primerTurnoId = MutableLiveData<String>().apply {
        value = ""
    }

    val primerTurnoId: LiveData<String> = _primerTurnoId

    private var _segundoTurnoId = MutableLiveData<String>().apply {
        value = ""
    }

    val segundoTurnoId: LiveData<String> = _segundoTurnoId

    private var _tercerTurnoId = MutableLiveData<String>().apply {
        value = ""
    }

    val tercerTurnoId: LiveData<String> = _tercerTurnoId

    fun setTurnosDisponibles(valor: String) {
        _turnosDisponibles.value = valor
    }

    fun setTurnosReservados(valor: String) {
        _turnosReservados.value = valor
    }

    fun setUltimoTurnoReservado(valor: String) {
        _ultimoTurnoReservado.value = valor
    }

    fun setUltimoTurnoDisponible(valor: String) {
        _ultimoTurnoDisponible.value = valor
    }

    fun setPrimerTurno(valor: String) {
        _primerTurno.value = valor
    }

    fun setSegundoTurno(valor: String) {
        _segundoTurno.value = valor
    }

    fun setTercerTurno(valor: String) {
        _tercerTurno.value = valor
    }

    fun setPrimerTurnoId(valor: String) {
        _primerTurnoId.value = valor
    }

    fun setSegundoTurnoId(valor: String) {
        _segundoTurnoId.value = valor
    }

    fun setTercerTurnoId(valor: String) {
        _tercerTurnoId.value = valor
    }

}


