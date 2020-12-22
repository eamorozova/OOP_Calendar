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

    }

    private fun setFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.base_layout, fragment)
            commit()
        }

    override fun sendInput(string: String) {
        val bundle = Bundle()
        bundle.putString("input", string)

        val transaction = supportFragmentManager.beginTransaction()
        val list = EventsRecyclerView()
        list.arguments = bundle

        transaction.replace(R.id.base_layout, list)
        //transaction.addToBackStack(null)
        //transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.commit()
    }

}