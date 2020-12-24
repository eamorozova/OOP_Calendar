package com.example.eventcalendar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.event_item.view.*
import java.util.*

class EventsRecyclerViewAdapter:
    RecyclerView.Adapter<EventsRecyclerViewAdapter.EventsViewHolder>(){

    //private val events: MutableMap<Date, EventItem> = mutableMapOf()

    private var events: MutableList<EventItem> = mutableListOf()

    class EventsViewHolder constructor(itemView: View):
        RecyclerView.ViewHolder(itemView) {
        private val eventTitle: TextView = itemView.event_text
        private val eventDate: TextView = itemView.event_date
        private val eventYear: TextView = itemView.event_year

        fun bind(event: EventItem) {
            eventTitle.text = event.title
            eventDate.text = event.date.subSequence(0, 5)
            eventYear.text = event.date.subSequence(6, 10)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsRecyclerViewAdapter.EventsViewHolder {
        return EventsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.event_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: EventsRecyclerViewAdapter.EventsViewHolder, position: Int) {
        holder.bind(events[position])
    }

    override fun getItemCount(): Int {
        return events.size
    }

    fun submitList(eventList: MutableList<EventItem>) {
        events = eventList
    }

    fun addEvent(event: EventItem) {
        events.add(event)
    }

    fun getList(): MutableList<EventItem> {
        return events
    }

    fun getEventId(date: String): Int {
        return events.indexOfFirst { eventItem ->
            (eventItem.date == date)
        }
    }
}