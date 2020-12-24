package com.example.eventcalendar

data class EventItem(val title: String, val date: String, val notes: String?) {
    override fun toString(): String {
        return "$title ($notes)"
    }
}