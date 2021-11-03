package com.tp3.asistenciamedica.ui.views

import android.app.AlertDialog
import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import com.tp3.asistenciamedica.R

class PickerEditText @kotlin.jvm.JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = R.attr.editTextStyle
) : AppCompatEditText(context, attrs, defStyleAttr), View.OnClickListener {

    var options: List<Any> = listOf()

    private var selectedItem: Any? = null

    init {
        setOnClickListener(this)
        isCursorVisible = false
        isFocusable = false
        isFocusableInTouchMode = false
    }

    override fun onClick(v: View?) {
        if (options.isEmpty()) return
        val texts = options.map { it.toString() }.toTypedArray()
        AlertDialog.Builder(context)
            .setSingleChoiceItems(texts, 0) { dialog, i ->
                setSelectedItem(options[i])
                dialog.dismiss()
            }.setTitle(hint)
            .create()
            .show()
    }

    fun setSelectedItem(item: Any) {
        selectedItem = item
        setText(item.toString())
    }

    fun getSelectedItem(): Any? {
        return selectedItem
    }
}