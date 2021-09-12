package com.lazypotato.flickrgallery.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateTimeUtil {

    fun parseDateTimeToAmPMString(dateString: String): String {
        var dateTime = ""

        val calendar: Calendar = GregorianCalendar()

        val givenFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        try {
            calendar.time = givenFormat.parse(dateString)

            val newFormat = SimpleDateFormat("dd MMM yyyy hh:mm aaa")
            dateTime = newFormat.format(calendar.time)
        } catch (e: ParseException) {
            dateTime = dateString

            e.printStackTrace()
        }

        return dateTime
    }

    fun getMillisFromDateTimeString(dateString: String): Long {
        val calendar: Calendar = GregorianCalendar()

        val givenFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        try {
            calendar.time = givenFormat.parse(dateString)
        } catch (e: ParseException) {
            e.printStackTrace()

            return 0
        }

        return calendar.timeInMillis
    }
}