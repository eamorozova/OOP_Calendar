package com.example.eventcalendar

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_event.*
import java.text.FieldPosition
import java.util.*

class EventInformation(private val eventItem: EventItem, private val position: Int) : Fragment(R.layout.fragment_event) {

    private val dateFormatter = DateFormatter()
    private val calendar = eventItem.date
    lateinit var communicator: Communicator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        event_title.isEnabled = false
        event_note.isEnabled = false

        event_title.setText(eventItem.title)
        event_note.setText(eventItem.notes)
        event_date.text = dateFormatter.formatDate(eventItem.date)
        event_time.text = dateFormatter.formatTime(eventItem.date)

        edit_info_button.setOnClickListener {
            setEditableMode()
        }
    }

    private val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, monthOfYear)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        event_date.text = dateFormatter.formatDate(calendar)
    }

    private val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)
        event_time.text = dateFormatter.formatTime(calendar)
    }

    private fun setEditableMode() {
        event_title.isEnabled = true
        event_note.isEnabled = true

        cancel_edit_button.visibility = View.VISIBLE
        done_edit_button.visibility = View.VISIBLE

        cancel_info_button.visibility = View.GONE
        edit_info_button.visibility = View.GONE

        event_date.setOnClickListener {
            DatePickerDialog(
                context!!,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        event_time.setOnClickListener {
            TimePickerDialog(
                context!!,
                timeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            ).show()
        }

        done_edit_button.setOnClickListener {
            if (event_title.text.isEmpty()) {
                Toast.makeText(context, "Title is empty", Toast.LENGTH_SHORT).show()
            } else {
                val title = event_title.text.toString()
                val notes = event_note.text.toString()
                communicator = activity as Communicator
                communicator.sendEvent(title, calendar as GregorianCalendar, notes, true, position)
            }
        }
        cancel_edit_button.setOnClickListener {
            activity!!.supportFragmentManager.popBackStack()
        }
    }
}
