package com.example.eventcalendar

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragmaent_events_recycler_view.*
import java.util.*

class EventsRecyclerView :
    Fragment(R.layout.fragmaent_events_recycler_view),
    EventsRecyclerViewAdapter.OnEventClickListener {

    private lateinit var eventsAdapter: EventsRecyclerViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val initList = EventReader().readFromFile(context!!)
        initRecyclerView(initList)

        val currentEvent = eventsAdapter.getEventId(Calendar.getInstance() as GregorianCalendar)
        recycler_view.scrollToPosition(currentEvent)

        listenInputs()

        recycler_search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                eventsAdapter.filter.filter(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                eventsAdapter.filter.filter(newText)
                return true
            }
        })
    }

    private fun initRecyclerView(events: MutableList<EventItem>) {
        recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            eventsAdapter = EventsRecyclerViewAdapter(this@EventsRecyclerView, events)
            adapter = eventsAdapter
        }
    }

    override fun onEventClick(position: Int) {
        val item = eventsAdapter.getEventById(position)
        val eventInformation = EventInformation(item, position)

        activity!!.supportFragmentManager.beginTransaction().apply {
            replace(R.id.base_layout, eventInformation)
            addToBackStack(null)
            commit()
        }
    }

    private fun listenInputs() {
        if (arguments?.containsKey("input") == true) {
            val clickedDate = arguments?.getSerializable("input") as GregorianCalendar
            val clickedPosition = eventsAdapter.getEventId(clickedDate)

            if (clickedPosition == -1) {
                Toast.makeText(context, "No future events", Toast.LENGTH_SHORT).show()
            } else {
                recycler_view.scrollToPosition(clickedPosition)
            }
        } else if (arguments?.containsKey("title") == true && arguments?.containsKey("date") == true) {
            val title = arguments?.getString("title")
            val notes = arguments?.getString("notes")
            val date = arguments?.getSerializable("date") as GregorianCalendar
            val isEdited = arguments?.getBoolean("isEdited")
            val position = arguments?.getInt("position")

            if (title != null && notes != null) {
                if (isEdited == true && position != null) {
                    eventsAdapter.editEvent(title, date, notes, position)
                    EventReader().writeToFile(context!!, eventsAdapter.getEventList())
                } else {
                    val newEvent = EventItem(title, date, notes)
                    eventsAdapter.addEvent(newEvent)
                    EventReader().writeToFile(context!!, eventsAdapter.getEventList())
                }
            }
        } else if (arguments?.containsKey("position") == true) {
            val position = arguments?.getInt("position")

            if (position != null) {
                eventsAdapter.removeEvent(position)
            }
            EventReader().writeToFile(context!!, eventsAdapter.getEventList())
        }
    }
}
