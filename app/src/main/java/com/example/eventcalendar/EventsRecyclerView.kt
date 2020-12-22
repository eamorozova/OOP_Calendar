package com.example.eventcalendar

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragmaent_events_recycler_view.*

class EventsRecyclerView : Fragment(R.layout.fragmaent_events_recycler_view) {
    private lateinit var eventsRecyclerViewAdapter: EventsRecyclerViewAdapter

    val example = mutableListOf<EventItem>(
        EventItem("Go out", "01.09.2000", "eeee"),
        EventItem("Finik", "01.09.2001", "eeee"),
        EventItem("Go out", "01.09.2005", "eeee"),
        EventItem("Loloped", "01.09.2009", "eeee"),
        EventItem("Go out", "01.09.2020", "eeee"),
        EventItem("Came here", "01.10.2020", "eeee"),
        EventItem("Loloped", "01.11.2020", "eeee"),
        EventItem("Go out", "10.12.2020", "eeee"),
        EventItem("Came here", "10.12.2020", "eeee"),
        EventItem("Loloped", "10.01.2021", "eeee"),
        EventItem("Go out", "01.02.2021", "eeee"),
        EventItem("Came here", "01.04.2021", "eeee"),
        EventItem("Loloped", "01.05.2021", "eeee"),
        EventItem("Go out", "01.09.2022", "eeee"),
        EventItem("Came here", "01.09.2022", "eeee"),
        EventItem("Loloped", "01.09.2022", "eeee"),
        EventItem("Go out", "01.10.2022", "eeee"),
        EventItem("Came here", "01.12.2022", "eeee")
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        eventsRecyclerViewAdapter.submitList(example)

        val date = arguments?.getString("input")
        if (date != null) {
            val search = eventsRecyclerViewAdapter.getEventId(date)
            recycler_view.scrollToPosition(search)
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