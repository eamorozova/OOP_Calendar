package com.example.eventcalendar

import java.text.SimpleDateFormat
import java.util.*

open class DateFormatter() {

    fun formatDate(calendar: Calendar): String {
        val formatter = SimpleDateFormat("dd MMMM yyyy", Locale.US)
        return "Date: ${formatter.format(calendar.time)}"
    }

    fun formatTime(calendar: Calendar): String {
        val formatter = SimpleDateFormat("HH:mm", Locale.US)
        return "Time: ${formatter.format(calendar.time)}"
    }
}
