package com.example.eventcalendar

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_add_event.*
import java.util.*

class AddEvent : Fragment(R.layout.fragment_add_event) {

    lateinit var communicator: Communicator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        done_button.setOnClickListener {
            onDoneClicked()
        }
        activity!!.fab.hide()
        activity!!.bottomAppBar.performHide()
    }

    private fun onDoneClicked() {
        val title = title_text.text.toString()
        val notes = note_text.text.toString()
        val date = GregorianCalendar(date_picker.year, date_picker.month, date_picker.dayOfMonth)

        communicator = activity as Communicator
        communicator.sendEvent(title, date, notes)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activity!!.fab.show()
        activity!!.bottomAppBar.performShow()
    }
}
