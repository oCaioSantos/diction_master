package com.example.dictionmaster.common.utils

import java.text.SimpleDateFormat
import java.util.Locale

class Utils {

    companion object {

        private var todayDate: String? = null

        fun getTodayDate(): String {
            return todayDate ?: run {
                val sdf = SimpleDateFormat(
                    "yyyy-MM-dd", Locale.getDefault()
                )
                todayDate = sdf.format(System.currentTimeMillis())
                return todayDate!!
            }
        }
    }

}