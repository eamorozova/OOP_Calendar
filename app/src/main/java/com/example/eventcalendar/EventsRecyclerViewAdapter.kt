package com.example.eventcalendar

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.event_item.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class EventsRecyclerViewAdapter(
    private val listener: OnEventClickListener,
    private var events: MutableList<EventItem>
) :
    RecyclerView.Adapter<EventsRecyclerViewAdapter.EventsViewHolder>(), Filterable {

    private var eventsFilterList = mutableListOf<EventItem>()

    init {
        events.sortBy { it.date }
        eventsFilterList = events
    }

    inner class EventsViewHolder constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private val eventTitle: TextView = itemView.item_text
        private val eventDate: TextView = itemView.item_day
        private val eventYear: TextView = itemView.item_year

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(event: EventItem) {
            eventTitle.text = event.title
            eventDate.text = SimpleDateFormat("dd MMM").format(event.date.time)
            eventYear.text = SimpleDateFormat("yyyy").format(event.date.time)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            itemView.setBackgroundColor(Color.rgb(255, 90, 104))
            listener.onEventClick(position)
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

    fun addEvent(event: EventItem) {
        events.add(event)
        events.sortBy { it.date }
    }

    fun getEventId(date: GregorianCalendar): Int {
        return events.indexOfFirst { (it.date >= date) }
    }

    fun getEventById(position: Int): EventItem {
        return events[position]
    }

    fun getEventList(): List<EventItem> {
        return events
    }

    fun editEvent(title: String, date: GregorianCalendar, notes: String, position: Int) {
        events[position].title = title
        events[position].date = date
        events[position].notes = notes
        notifyDataSetChanged()
    }

    fun removeEvent(position: Int) {
        events.removeAt(position)
        notifyDataSetChanged()
    }

    interface OnEventClickListener {
        fun onEventClick(position: Int)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()

                if (constraint == null || constraint.length < 0) {
                    filterResults.values = eventsFilterList
                } else {
                    val filterPattern = constraint.toString().toLowerCase().trim()
                    val filteredEvents = ArrayList<EventItem>()

                    eventsFilterList.forEach {
                        if (it.title.toLowerCase().contains(filterPattern))
                            filteredEvents.add(it)
                    }

                    filterResults.values = filteredEvents
                }
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                events = results?.values as ArrayList<EventItem>
                notifyDataSetChanged()
            }
        }
    }
}
