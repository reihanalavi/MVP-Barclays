package com.smansara.tugasque.utils

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat

object DateParser {

    @SuppressLint("SimpleDateFormat")
    private fun formatLongDate(date: String, format: String): String {
        var result = ""

        val oldDate = SimpleDateFormat("yyyy-MM-dd")
        try {
            val old = oldDate.parse(date)
            val newFormat = SimpleDateFormat(format)
            result = newFormat.format(old)
        } catch (e: ParseException) {
            result = date
            e.printStackTrace()
        }

        return result
    }

    fun getLongDate(date: String): String {
        return formatLongDate(date, "EEEE, dd MMMM yyyy")
    }

}
