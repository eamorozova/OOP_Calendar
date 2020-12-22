package com.example.eventcalendar

data class EventItem(val title: String, val date: String, val description: String?) {
    override fun toString(): String {
        return "$title ($description)"
    }
}