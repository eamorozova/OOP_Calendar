package com.example.eventcalendar

import java.util.*

interface Communicator {

    fun sendInput(clickedDate: GregorianCalendar)

    fun sendEvent(title: String, date: GregorianCalendar, note: String)

}