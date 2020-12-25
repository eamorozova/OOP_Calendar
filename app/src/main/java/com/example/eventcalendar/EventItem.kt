package com.example.eventcalendar

import java.util.*

data class EventItem(var title: String, var date: GregorianCalendar, var notes: String?) {

    override fun toString(): String {
        return "$title ($notes)"
    }
}
