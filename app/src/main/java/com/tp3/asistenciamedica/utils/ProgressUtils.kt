package com.tp3.asistenciamedica.utils

import android.app.ProgressDialog
import android.content.Context
import com.tp3.asistenciamedica.R

object ProgressUtils {

    var progressDialog: ProgressDialog? = null

    fun showLoading(context: Context){
        progressDialog = ProgressDialog(context);
        progressDialog!!.setMessage(context.getString(R.string.loading));
        progressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog!!.show();
    }

    fun hideLoading(){
        progressDialog?.hide()
        progressDialog = null
    }


}