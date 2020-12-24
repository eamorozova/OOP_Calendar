package com.example.eventcalendar

import android.os.Bundle
import android.widget.SearchView
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

        bottomNavigationView.background = null
        bottomNavigationView.menu.getItem(2).isEnabled = false

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_calendar -> setFragment(calendarView)
                R.id.menu_events -> setFragment(eventsRecyclerView)
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

    override fun sendEvent(title: String, date: GregorianCalendar, note: String) {
        val bundle = Bundle()

        bundle.putString("title", title)
        bundle.putString("notes", note)
        bundle.putSerializable("date", date)
        bundleToList(bundle)
    }

    private fun bundleToList(bundle: Bundle) {
        val list = EventsRecyclerView()
        list.arguments = bundle
        setFragment(list)
    }
}
