package com.example.eventcalendar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragmaent_events_recycler_view.*
import java.util.*

class MainActivity : AppCompatActivity(), Communicator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val calendarView = CalendarView()
        val eventsRecyclerView = EventsRecyclerView()

        setFragment(calendarView)

        bottomNavigationView.background = null
        bottomNavigationView.menu.getItem(2).isEnabled = false

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_calendar -> setFragment(calendarView)
                R.id.menu_events -> setFragment(eventsRecyclerView)
                R.id.menu_search -> {
                    setFragment(eventsRecyclerView)
                }
            }
            true
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

        bundle.putString("title", title)
        bundle.putString("notes", note)
        bundle.putSerializable("date", date)
        bundle.putBoolean("isEdited", isEdited)
        if (position != null) {
            bundle.putInt("position", position)
        }
        bundleToList(bundle)
    }

    private fun bundleToList(bundle: Bundle) {
        val list = EventsRecyclerView()
        list.arguments = bundle
        setFragment(list)
    }
}
