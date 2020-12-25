package com.example.eventcalendar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), Communicator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val calendarView = CalendarView()
        val eventsRecyclerView = EventsRecyclerView()

        setFragment(calendarView)

        bottomNavigationView.apply {
            background = null
            menu.getItem(1).isEnabled = false
            setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.menu_calendar -> setFragment(calendarView)
                    R.id.menu_events -> setFragment(eventsRecyclerView)
                }
                true
            }
        }

        fab.setOnClickListener {
            val addEvent = AddEvent()
            setFragment(addEvent)
        }
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.base_layout, fragment)
            addToBackStack(null)
            commit()
        }
    }

    override fun sendInput(clickedDate: GregorianCalendar) {
        val bundle = Bundle()

        bundle.putSerializable("input", clickedDate)
        bundleToList(bundle)
    }

    override fun sendEvent(title: String, date: GregorianCalendar, note: String, isEdited: Boolean, position: Int?) {
        val bundle = Bundle()

        bundle.apply {
            putString("title", title)
            putString("notes", note)
            putSerializable("date", date)
            putBoolean("isEdited", isEdited)
            if (position != null) {
                putInt("position", position)
            }
        }

        bundleToList(bundle)
    }

    override fun sendPosition(position: Int) {
        val bundle = Bundle()

        bundle.putInt("position", position)
        bundleToList(bundle)
    }

    private fun bundleToList(bundle: Bundle) {
        val list = EventsRecyclerView()
        list.arguments = bundle
        setFragment(list)
    }
}
