package com.tp3.asistenciamedica.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DateUtils {

    companion object {

        fun isValidDate(date: String, format: String): Boolean {
            val formatter = SimpleDateFormat(format, Locale.getDefault())
            formatter.isLenient = false

            return try {
                formatter.parse(date)
                true
            } catch (e: ParseException) {
                false
            }
        }
    }

}