package com.example.moviebooking2

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BookingActivity : AppCompatActivity() {

    private lateinit var databaseHelper: MovieDatabaseHelper
    private lateinit var adapter: BookingAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)

        databaseHelper = MovieDatabaseHelper(this)

        val bookings = databaseHelper.getAllBookings().toMutableList()
        adapter = BookingAdapter(bookings) { booking ->
            databaseHelper.deleteBooking(booking.id)
            refreshData()
        }

        findViewById<RecyclerView>(R.id.rvBookings).apply {
            layoutManager = LinearLayoutManager(this@BookingActivity)
            adapter = this@BookingActivity.adapter
        }

        findViewById<Button>(R.id.btnAddBooking).setOnClickListener {
            val movieName = findViewById<EditText>(R.id.etMovieName).text.toString()
            val date = findViewById<EditText>(R.id.etDate).text.toString()
            val time = findViewById<EditText>(R.id.etTime).text.toString()
            val tickets = findViewById<EditText>(R.id.etTickets).text.toString().toIntOrNull() ?: 0

            if (movieName.isNotBlank() && date.isNotBlank() && time.isNotBlank() && tickets > 0) {
                databaseHelper.insertBooking(movieName, date, time, tickets)
                refreshData()
            }
        }
    }

     fun refreshData() {
        val newBookings = databaseHelper.getAllBookings()
        adapter.updateData(newBookings)
    }
}

