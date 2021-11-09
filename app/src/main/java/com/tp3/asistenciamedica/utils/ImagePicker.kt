package com.tp3.asistenciamedica.utils

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.tp3.asistenciamedica.ui.BaseActivity
import com.tp3.asistenciamedica.ui.OnActivityResult
import com.tp3.asistenciamedica.ui.OnRequestPermissionsResult

class ImagePicker(private val activity: BaseActivity) : OnRequestPermissionsResult,
    OnActivityResult {

    lateinit var callback: (Uri?)->Unit

    fun pick(callback: (Uri?)->Unit){
        this.callback = callback
        if (ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            activity.addPermissionWaiter(this)
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ),
                0
            )
            return
        }
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        activity.addResultWaiter(this)
        try {
            activity.startActivityForResult(intent, 0)
        } catch (e: ActivityNotFoundException) {

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        activity.removePermissionWaiter(this)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            pick(callback)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            val fileUri = data?.data
            callback(fileUri)
        }
    }

}