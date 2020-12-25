package com.example.eventcalendar

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_add_event.*
import java.util.*

class AddEvent : Fragment(R.layout.fragment_add_event) {

    lateinit var communicator: Communicator
    private val calendar = Calendar.getInstance()
    private var dateFormatter = DateFormatter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity!!.fab.hide()
        activity!!.bottomAppBar.performHide()

        date_input.text = dateFormatter.formatDate(calendar)
        time_input.text = dateFormatter.formatTime(calendar)

        date_input.setOnClickListener {
            DatePickerDialog(
                context!!,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),

            ).show()
        }

        time_input.setOnClickListener {
            TimePickerDialog(
                context!!,
                timeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            ).show()
        }

        done_button.setOnClickListener {
            if (title_text.text.isEmpty()) {
                Toast.makeText(context, "Title is empty", Toast.LENGTH_SHORT).show()
            } else {
                val title = title_text.text.toString()
                val notes = note_text.text.toString()
                communicator = activity as Communicator
                communicator.sendEvent(title, calendar as GregorianCalendar, notes, false, null)
            }
        }

        cancel_button.setOnClickListener {
            activity!!.supportFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activity!!.fab.show()
        activity!!.bottomAppBar.performShow()
    }

    private val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, monthOfYear)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        date_input.text = dateFormatter.formatDate(calendar)
    }

    private val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)
        time_input.text = dateFormatter.formatTime(calendar)
    }
}
