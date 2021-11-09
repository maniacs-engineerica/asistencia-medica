package com.tp3.asistenciamedica.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity: AppCompatActivity() {

    private var waiterForPermissionsResults: MutableList<OnRequestPermissionsResult> = mutableListOf()
    private var waiterForActivityResults: MutableList<OnActivityResult> = mutableListOf()

    fun addPermissionWaiter(waiter: OnRequestPermissionsResult){
        waiterForPermissionsResults.add(waiter)
    }

    fun removePermissionWaiter(waiter: OnRequestPermissionsResult){
        waiterForPermissionsResults.remove(waiter)
    }

    fun addResultWaiter(waiter: OnActivityResult){
        waiterForActivityResults.add(waiter)
    }

    fun removeResultWaiter(waiter: OnActivityResult){
        waiterForActivityResults.remove(waiter)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        waiterForPermissionsResults.forEach { it.onRequestPermissionsResult(requestCode, permissions, grantResults) }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        waiterForActivityResults.forEach { it.onActivityResult(requestCode, resultCode, data) }
        super.onActivityResult(requestCode, resultCode, data)
    }
}

interface OnRequestPermissionsResult {
    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    )
}

interface OnActivityResult {
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
}