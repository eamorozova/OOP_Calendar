package com.example.eventcalendar

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragmaent_events_recycler_view.*
import java.lang.reflect.Type
import java.util.*

class EventsRecyclerView : Fragment(R.layout.fragmaent_events_recycler_view) {

    private val gson = Gson()
    private val type: Type = object : TypeToken<MutableList<EventItem>>() {}.type

    private lateinit var eventsRecyclerViewAdapter: EventsRecyclerViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()

        val string = EventReader().readFromFile(context!!, "events.json")
        val eve = gson.fromJson<MutableList<EventItem>>(string, type)

        eventsRecyclerViewAdapter.submitList(eve)

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
                val jsonString = gson.toJson(eventsRecyclerViewAdapter.getEventList())
                EventReader().writeToFile(context!!, "events.json", jsonString)
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
