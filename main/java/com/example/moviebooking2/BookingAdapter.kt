package com.example.moviebooking2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BookingAdapter(
    private val bookings: MutableList<MovieBooking>,
    private val onDeleteClick: (MovieBooking) -> Unit
) : RecyclerView.Adapter<BookingAdapter.BookingViewHolder>() {

    class BookingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvMovieName: TextView = view.findViewById(R.id.tvMovieName)
        val tvDate: TextView = view.findViewById(R.id.tvDate)
        val tvTime: TextView = view.findViewById(R.id.tvTime)
        val tvTickets: TextView = view.findViewById(R.id.tvTickets)
        val btnDelete: Button = view.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_booking, parent, false)
        return BookingViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookingViewHolder, position: Int) {
        val booking = bookings[position]
        holder.tvMovieName.text = booking.movieName
        holder.tvDate.text = "Date: ${booking.date}"
        holder.tvTime.text = "Time: ${booking.time}"
        holder.tvTickets.text = "Tickets: ${booking.tickets}"
        holder.btnDelete.setOnClickListener { onDeleteClick(booking) }
    }

    override fun getItemCount() = bookings.size

    fun updateData(newBookings: List<MovieBooking>) {
        bookings.clear()
        bookings.addAll(newBookings)
        notifyDataSetChanged()
    }
}

