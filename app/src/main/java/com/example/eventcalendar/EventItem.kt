package com.example.eventcalendar

import java.util.*

data class EventItem(val title: String, val date: GregorianCalendar, val notes: String?) {
    override fun toString(): String {
        return "$title ($notes)"
    }
}