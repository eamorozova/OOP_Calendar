package com.example.eventcalendar

import android.icu.util.Calendar
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_calendar_view.*
import java.util.*

class CalendarView : Fragment(R.layout.fragment_calendar_view) {

    lateinit var communicator: Communicator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        calendar_view.firstDayOfWeek = Calendar.MONDAY

        communicator = activity as Communicator

        calendar_view.setOnDateChangeListener { view, year, month, dayOfMonth ->
            communicator.sendInput(GregorianCalendar(year, month, dayOfMonth))
        }
    }
}
