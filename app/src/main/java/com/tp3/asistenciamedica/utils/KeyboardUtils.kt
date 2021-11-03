package com.tp3.asistenciamedica.utils

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import com.tp3.asistenciamedica.AsistenciaMedicaApplication

class KeyboardUtils {

    companion object {
        fun close(activity: Activity){
            val imm = AsistenciaMedicaApplication.applicationContext().getSystemService(Context.INPUT_METHOD_SERVICE)
                    as InputMethodManager
            activity.currentFocus?.let {
                imm.hideSoftInputFromWindow(it.windowToken, 0);
            }
        }
    }

}