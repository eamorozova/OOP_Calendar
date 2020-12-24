package com.example.eventcalendar

interface Communicator {

    fun sendInput(string: String)

    fun sendEvent(title: String, date: String, note: String)

}