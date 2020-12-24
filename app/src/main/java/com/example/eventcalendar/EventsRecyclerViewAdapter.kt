package com.example.eventcalendar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.event_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class EventsRecyclerViewAdapter : RecyclerView.Adapter<EventsRecyclerViewAdapter.EventsViewHolder>() {

    private var events = mutableListOf<EventItem>()

    class EventsViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val eventTitle: TextView = itemView.event_text
        private val eventDate: TextView = itemView.event_date
        private val eventYear: TextView = itemView.event_year

        fun bind(event: EventItem) {
            eventTitle.text = event.title
            eventDate.text = SimpleDateFormat("dd MMM").format(event.date.time)
            eventYear.text = SimpleDateFormat("yyyy").format(event.date.time)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
        return EventsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.event_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        holder.bind(events[position])
    }

    override fun getItemCount(): Int {
        return events.size
    }

    fun submitList(eventList: MutableList<EventItem>) {
        events = eventList
        events.sortBy { it.date }
    }

    fun addEvent(event: EventItem) {
        events.add(event)
        events.sortBy { it.date }
    }

    fun getEventId(date: GregorianCalendar): Int {
        return events.indexOfFirst { eventItem ->
            (eventItem.date == date)
        }
    }

    fun getEventList(): List<EventItem> {
        return events
    }
}
