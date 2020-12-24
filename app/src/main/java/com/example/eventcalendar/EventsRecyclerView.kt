package com.example.eventcalendar

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragmaent_events_recycler_view.*
import java.util.*

class EventsRecyclerView : Fragment(R.layout.fragmaent_events_recycler_view) {

    private lateinit var eventsRecyclerViewAdapter: EventsRecyclerViewAdapter

    val example = mutableListOf<EventItem>(
        EventItem("Go out", GregorianCalendar(2060, 11, 1), "eeee"),
        EventItem("Finik", GregorianCalendar(2020, 11, 2), "eeee"),
        EventItem("Go out", GregorianCalendar(2080, 11, 2), "eeee"),
        EventItem("Loloped", GregorianCalendar(2000, 11, 20), "eeee"),
        EventItem("Go out", GregorianCalendar(2000, 11, 22), "eeee"),
        EventItem("Came here", GregorianCalendar(2000, 11, 21), "eeee"),
        EventItem("Go out", GregorianCalendar(2000, 11, 1), "eeee"),
        EventItem("Finik", GregorianCalendar(2000, 11, 2), "eeee"),
        EventItem("Go out", GregorianCalendar(2000, 8, 2), "eeee"),
        EventItem("Loloped", GregorianCalendar(2000, 11, 20), "eeee"),
        EventItem("Go out", GregorianCalendar(2000, 11, 22), "eeee"),
        EventItem("Came here", GregorianCalendar(200, 11, 21), "eeee"),
        EventItem("Go out", GregorianCalendar(2000, 11, 1), "eeee"),
        EventItem("Finik", GregorianCalendar(2010, 11, 2), "eeee"),
        EventItem("Go out", GregorianCalendar(2020, 11, 2), "eeee"),
        EventItem("Loloped", GregorianCalendar(2020, 11, 20), "eeee"),
        EventItem("Go out", GregorianCalendar(2020, 11, 22), "eeee"),
        EventItem("Came here", GregorianCalendar(2020, 11, 21), "eeee")
    )


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        eventsRecyclerViewAdapter.submitList(example)

        if (arguments?.containsKey("input") == true) {
            val clickedDate = arguments?.getSerializable("input") as GregorianCalendar
            val search = eventsRecyclerViewAdapter.getEventId(clickedDate)
            recycler_view.scrollToPosition(search)
        }

        if (arguments?.containsKey("title") == true && arguments?.containsKey("date") == true) {
            val title = arguments?.getString("title")
            val notes = arguments?.getString("notes")
            val date = arguments?.getSerializable("date") as GregorianCalendar

            if (title != null && notes != null) {
                val newEvent = EventItem(title, date, notes)
                eventsRecyclerViewAdapter.addEvent(newEvent)
            }
        }
    }

    private fun initRecyclerView() {
        recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            eventsRecyclerViewAdapter = EventsRecyclerViewAdapter()
            adapter = eventsRecyclerViewAdapter
        }
    }
}