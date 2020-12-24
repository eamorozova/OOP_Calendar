package com.example.eventcalendar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), Communicator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val calendarView = CalendarView()
        val eventsRecyclerView = EventsRecyclerView()

        setFragment(calendarView)

        bottomNavigationView.background = null
        bottomNavigationView.menu.getItem(1).isEnabled = false

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.menu_calendar -> setFragment(calendarView)
                R.id.menu_events -> setFragment(eventsRecyclerView)
            }
            true
        }

        fab.setOnClickListener {
            bottomAppBar.performHide()
            fab.hide()
            val addEvent = AddEvent()
            setFragment(addEvent)
        }
    }

    private fun setFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.base_layout, fragment)
            commit()
        }

    override fun sendInput(string: String) {
        val bundle = Bundle()

        bundle.putString("input", string)
        bundleToList(bundle)
    }

    override fun sendEvent(title: String, date: String, note: String) {
        val bundle = Bundle()

        bundle.putString("title", title)
        bundle.putString("notes", note)
        bundle.putString("date", date)
        bundleToList(bundle)
    }

    private fun bundleToList(bundle: Bundle) {
        val list = EventsRecyclerView()
        list.arguments = bundle
        setFragment(list)
    }
}