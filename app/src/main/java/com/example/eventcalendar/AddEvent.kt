package com.example.eventcalendar

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_add_event.*

class AddEvent: Fragment(R.layout.fragment_add_event) {

    lateinit var communicator: Communicator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        submit()

//        communicator = activity as Communicator
//
//        val title = title_text.text.toString()
//        val notes = note_text.text.toString()
//
//        val date = "${date_picker.dayOfMonth}.${date_picker.month + 1}.${date_picker.year}"
//
//        communicator.sendEvent(title, notes, date)
    }

    fun submit() {
        done_button.setOnClickListener {
            communicator = activity as Communicator
            val title = title_text.text.toString()
            val notes = note_text.text.toString()

            val date = "${date_picker.dayOfMonth}.${date_picker.month + 1}.${date_picker.year}"
            communicator.sendEvent(title, date, notes)
        }
    }

}